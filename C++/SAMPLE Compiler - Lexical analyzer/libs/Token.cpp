#include <string>

class Token
{
public:
    Token(std::string val, unsigned int type, int constid =0)
    {
        this->val = val;
        this->type = type;
        this->constid = constid;
    }

    ~Token() {}

    std::string val;
    unsigned int type;
    int constid;
};
