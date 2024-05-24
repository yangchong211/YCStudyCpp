#pragma once

#include"student.h"

class Graduate : virtual public Student {
public:
    string direction;
    string adviserName;

    virtual void displayDetails();

    virtual void inputData();

    string getStudentNo() {
        return studentno;
    }

};

void Graduate::inputData() {
    Student::inputData();
    cout << "研究方向：";
    cin >> direction;
    cout << "导师姓名：";
    cin >> adviserName;
}

void Graduate::displayDetails() {
    Student::displayDetails();
    cout << "研究方向：" << direction << endl;
    cout << "导师姓名：" << adviserName << endl;
}