// From: https://stackoverflow.com/questions/8152720/correct-way-to-inherit-from-stdexception

#include <string>

class InvaildTokenException: public std::exception
{
public:
    explicit InvaildTokenException(const char* message):
    msg_(message) {}

    explicit InvaildTokenException(const std::string& message):
    msg_(message) {}

    virtual ~InvaildTokenException() throw () {}

    virtual const char* what() const throw ()
    {
        return msg_.c_str();
    }

protected:
    std::string msg_;
};
