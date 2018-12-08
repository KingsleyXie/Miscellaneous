#include <map>
#include <vector>
#include <string>
#include <fstream>
#include <iostream>
#include <iomanip>

#include "InvaildTokenException.cpp"
#include "Token.cpp"

class Scanner
{
public:
    Scanner() {}
    ~Scanner() {}

    std::vector<Token> tokens;
    std::map<std::string, int> constants;

    void setFile()
    {
        std::string filename;
        bool succ = false;

        while (!succ)
        {
            std::cout << "Please input test program's basename > ";
            std::cin >> filename;

            filename = "tests/" + filename + ".in";

            ifs.open(filename, std::ifstream::in);

            if (!ifs.fail()) succ = true;
            else std::cout << "File `" + filename + "` does not exist!\n";
        }
    }

    void scanTokens()
    {
        while (std::getline(ifs, line))
        {
            lineCnt++;
            charPos = 0;

            try {
                scanLine();
            } catch (InvaildTokenException& e) {
                std::cout << "\n\tInvaild token at line " << lineCnt;
                std::cout << ": " << e.what() << "\n";
            }
        }
    }

    void printTokens()
    {
        int cnt = 0;
        std::cout << "\n";
        for (Token token : tokens)
        {
            std::cout << "(" << std::setw(2) << token.type << ", ";

            std::cout << std::setw(2);
            if (token.type >= WORD_IDENTIFIER && token.type <= WORD_CHAR) {
                std::cout << token.constid;
            } else {
                std::cout << "-";
            }

            std::cout << ")" << "\t";

            cnt++;
            if (!(cnt % 5)) std::cout << "\n";
        }
        std::cout << "\n\n";
    }

private:
    enum StateType
    {
        START, END, IDENTIFIER, NUMBER,
        GT, LT, DOT,
        COLON, QUOTE, SLASH, COMMENTL, COMMENTR
    } state;

    std::map<std::string, unsigned int> words = {
        {"and", 1}, {"array", 2}, {"begin", 3}, {"bool", 4}, {"call", 5},
        {"case", 6}, {"char", 7}, {"constant", 8}, {"dim", 9}, {"do", 10},
        {"else", 11}, {"end", 12}, {"false", 13}, {"for", 14}, {"if", 15},
        {"input", 16}, {"integer", 17}, {"not", 18}, {"of", 19}, {"or", 20},
        {"output", 21}, {"procedure", 22}, {"program", 23}, {"read", 24}, {"real", 25},
        {"repeat", 26}, {"set", 27}, {"stop", 28}, {"then", 29}, {"to", 30},
        {"true", 31}, {"until", 32}, {"var", 33}, {"while", 34}, {"write", 35},
        /* 36: identifier, 37: number, 38: char */ {"(", 39}, {")", 40},
        {"*", 41}, {"*/", 42}, {"+", 43}, {",", 44}, {"-", 45},
        {".", 46}, {"..", 47}, {"/", 48}, {"/*", 49}, {":", 50},
        {":=", 51}, {";", 52}, {"<", 53}, {"<=", 54}, {"<>", 55},
        {"=", 56}, {">", 57}, {">=", 58}, {"[", 59}, {"]", 60}
    };

    unsigned int WORD_IDENTIFIER = 36, WORD_NUMBER = 37, WORD_CHAR = 38;
    unsigned int WORD_COMMENT_START = 49, WORD_COMMENT_END = 42;

    std::ifstream ifs;
    std::string line;

    unsigned int tokenT, lineCnt = 0, charPos = 0;
    bool discardCurr;

    char readNextChar()
    {
        return line[charPos++];
    }

    void backward()
    {
        charPos--;
        discardCurr = true;
    }

    void scanLine()
    {
        while (charPos < line.size()) getNextToken();
    }



    void getNextToken()
    {
        state = START;
        std::string currWord = "";

        while ((state != END) && (charPos <= line.size()))
        {
            discardCurr = false;
            char curr = readNextChar();
            switch (state)
            {
                case START:
                {
                    switch (curr)
                    {
                        case ':': state = COLON; break;
                        case '/': state = SLASH; break;
                        case '>': state = GT; break;
                        case '<': state = LT; break;
                        case '.': state = DOT; break;

                        case '\'':
                        {
                            discardCurr = true;
                            state = QUOTE;
                            break;
                        }

                        case ' ':
                        case '\t':
                        case '\r': // Escape `\r` for Windows files
                        case 0: // Escape NULL char
                        {
                            discardCurr = true;
                            break;
                        }

                        default:
                        {
                            if (std::isalpha(curr)) state = IDENTIFIER;
                            else if (std::isdigit(curr)) state = NUMBER;
                            else
                            {
                                state = END;
                                std::string ch(1, curr);

                                if (words.find(ch) != words.end()) {
                                    tokenT = words[ch];
                                }
                                else {
                                    throw InvaildTokenException("char `" + ch + "` is not in the keyword list");
                                }
                            }
                            break;
                        }
                    }
                    break;
                }

                case COLON:
                {
                    state = END;

                    if (curr == '=') tokenT = words[":="];
                    else {
                        tokenT = words[":"];
                        backward();
                    }
                    break;
                }

                case GT:
                {
                    state = END;

                    if (curr == '=') tokenT = words[">="];
                    else {
                        tokenT = words[">"];
                        backward();
                    }
                    break;
                }

                case LT:
                {
                    state = END;

                    if (curr == '=') tokenT = words["<="];
                    else if (curr == '>') tokenT = words["<>"];
                    else {
                        tokenT = words["<"];
                        backward();
                    }
                    break;
                }

                case DOT:
                {
                    state = END;

                    if (curr == '.') tokenT = words[".."];
                    else {
                        tokenT = words["."];
                        backward();
                    }
                    break;
                }

                case QUOTE:
                {
                    if (curr == '\'') {
                        state = END;
                        discardCurr = true;
                        tokenT = WORD_CHAR;
                    } else if (curr == '\n' || curr == 0) {
                        throw InvaildTokenException("line ended before char `" + currWord + "` finishes");
                    }
                    break;
                }

                case SLASH:
                {
                    if (curr == '*') state = COMMENTL;
                    else {
                        state = END;
                        tokenT = words["/"];
                        backward();
                    }
                    break;
                }

                case COMMENTL:
                {
                    if (curr == '*') state = COMMENTR;
                    else if (curr == '\n' || curr == 0) {
                        throw InvaildTokenException("`*/` is required before this comment finishes");
                    }
                    break;
                }

                case COMMENTR:
                {
                    if (curr == '/') {
                        state = END;
                        tokenT = words["*/"];
                    } else if (curr == '\n' || curr == 0) {
                        throw InvaildTokenException("`/` is required before this comment finishes");
                    } else {
                        state = COMMENTL;
                    }
                    break;
                }

                case NUMBER:
                {
                    if (!(std::isdigit(curr) || std::isalpha(curr))) {
                        state = END;
                        tokenT = WORD_NUMBER;
                        backward();
                    } else if (std::isalpha(curr)) {
                        throw InvaildTokenException("non-digital char following the number value `" + currWord + "`");
                    }
                    break;
                }

                case IDENTIFIER:
                {
                    if (!(std::isdigit(curr) || std::isalpha(curr))) {
                        state = END;
                        tokenT = WORD_IDENTIFIER;
                        backward();
                    }
                    break;
                }

                default: {}
            }

            if (!discardCurr) {
                std::string ch(1, curr);
                currWord += ch;
            }

            if (state == END)
            {
                if ((tokenT == WORD_IDENTIFIER)
                    && (words.find(currWord) != words.end())) {
                    tokenT = words[currWord];
                }

                if (tokenT != WORD_COMMENT_END) {
                    Token token(currWord, tokenT);

                    if (tokenT >= WORD_IDENTIFIER && tokenT <= WORD_CHAR)
                    {
                        if (constants.find(currWord) == constants.end()) {
                            token.constid = constants.size() + 1;
                            constants.insert(std::pair<std::string, int>(currWord, token.constid));
                        }
                        else {
                            token.constid = constants.find(currWord)->second;
                        }
                    }

                    tokens.push_back(token);
                }
            }
        }
    }
};
