#pragma once

class Student :virtual public Person{
public:
    string studentno;
    char schoolName[20];
    int classIn;
    virtual void displayDetails();
    virtual void inputData();

};
void Student::inputData()
{
    Person::inputData();
    cout << "学号：";
    cin >> studentno;
    cout << "学校：";
    cin >> schoolName;
    cout << "班级：";
    cin >> classIn;

}

void Student::displayDetails()
{
    Person::displayDetails();
    cout << "学号：" << studentno << endl;
    cout << "学校：" << schoolName << endl;
    cout << "班级：" << classIn << endl;
}