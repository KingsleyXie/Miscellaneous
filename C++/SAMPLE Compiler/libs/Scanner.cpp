#include <string>
#include <vector>
#include <fstream>
#include <iostream>

#include "InvaildTokenException.cpp"
#include "Token.cpp"

class Scanner
{
public:
    Scanner() {}
    ~Scanner() {}

    void setFile()
    {
        std::string filename;
        bool succ = false;

        while (!succ)
        {
            std::cout << "Please input test program's basename > ";
            std::cin >> filename;

            filename = "tests/" + filename + ".in";

            this->ifs.open(filename, std::ifstream::in);

            if (!this->ifs.fail()) succ = true;
            else std::cout << "File `" + filename + "` does not exist!\n";
        }
    }

    void scanTokens()
    {
        std::string line;
        while (std::getline(this->ifs, line))
        {
            this->lineCnt++;
            try {
                scanLine(line);
            } catch (InvaildTokenException& e) {
                std::cout << "Invaild token at line " << this->lineCnt;
                std::cout << ": " << e.what() << "\n";
            }
        }
    }

    std::vector<Token> getTokens()
    {
        return this->tokens;
    }

private:
    enum StateType
    {
        START,
        IDENTIFIER, NUMBER, STRING,
        ASSIGN, SLASH, COMMENTL, COMMENTR,
        GT, LT, DOT,
        END
    } state;

    int lineCnt =0;

    std::ifstream ifs;
    std::vector<Token> tokens;

    void scanLine(std::string line)
    {
        throw InvaildTokenException("Reading this: " + line);
    }

    char getCurrChar()
    {
        return 't';
    }

    void backward() {}
};
