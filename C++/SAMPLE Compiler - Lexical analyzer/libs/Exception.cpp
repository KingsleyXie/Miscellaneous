// From: https://stackoverflow.com/questions/8152720/correct-way-to-inherit-from-stdexception

#include <string>

class Exception: public std::exception
{
public:
    explicit Exception(const char* message):
    msg_(message) {}

    explicit Exception(const std::string& message):
    msg_(message) {}

    virtual ~Exception() throw () {}

    virtual const char* what() const throw ()
    {
        return msg_.c_str();
    }

protected:
    std::string msg_;
};
