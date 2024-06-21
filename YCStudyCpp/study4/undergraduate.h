#pragma once

#include"student.h"

class Undergraduate : virtual public Student {
private:
    string major;
public:
    string getStudentNo() {
        return studentno;
    }

    virtual void inputData() {
        Student::inputData();
        cout << "输入专业：";
        cin >> major;
    }

    virtual void displayDetails() {
        Student::displayDetails();
        cout << "专业：" << major << endl;
    }
};