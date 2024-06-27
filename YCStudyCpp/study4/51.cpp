
#include <iostream>
#include <fstream>
#include <string>

using namespace std;

class Date; //日期：年、月、日
class Person;//基类：ID、name、gender、Date
class Student;//studentNo、schoolName、classIn
class Teacher;

class UnderGraduate;

class Graduate;

class TA;

class UnderGraduateManager;

class GraduateManager;

class TAManager;

class Date {
private:
    int year;
    int month;
    int day;
public:
    Date() {
        year = 0;
        month = 0;
        day = 0;
    }

    Date(int y, int m, int d) {
        year = y;
        month = m;
        day = d;
    }

    Date(Date &d) {
        year = d.year;
        month = d.month;
        day = d.day;
    } //初始化
    ~Date() {};

    void setYear(int y) { year = y; }

    void setMonth(int m) { month = m; }

    void setDay(int d) { day = d; }

    int getYear() { return year; }

    int getMonth() { return month; }

    int getDay() { return day; }

    void inputDate() {
        cout << "year： ";
        cin >> year;
        cout << "month： ";
        cin >> month;
        cout << "day3： ";
        cin >> day;
    }

    void displayDate() { cout << year << "-" << month << "-" << day << endl; }
};

class Person {
protected:
    char id[18];
    char name[20];
    char gender[6];
    Date birthday;
public:
    Person();

    Person(char *sname, char *sid, char *sgender, int y, int m, int d);

    ~Person() {};

    void setName(char *sname) { strcpy(name, sname); }

    char *getName() { return name; }

    void setId(char *sid) { strcpy(id, sid); }

    char *getId() { return id; }

    void setGender(char *sgender) { strcpy(gender, sgender); }

    char *getGender() { return gender; }

    void setBirthday(Date d) { birthday = d; }

    Date getBirthday() { return birthday; }

    virtual void inputData();//输入数据
    virtual void displayDetails(); //显示数据
};

Person::Person() {//默认构造
    strcpy(name, "null");
    strcpy(gender, "男");
    strcpy(id, "0");
    birthday = Date(1980, 1, 1);
}

//自定义构造
Person::Person(char *sname, char *sid, char *sgender, int y, int m, int d) {
    strcpy(name, sname);
    strcpy(gender, sid);
    strcpy(id, sgender);
}

void Person::inputData() {//修改或输入数据
    cout << "name: ";
    cin >> name;
    cout << "ID: ";
    cin >> id;
    cout << "gender: ";
    cin >> gender;
    cout << "birthday: " << endl;
    birthday.inputDate();
}

void Person::displayDetails() {//输出数据
    cout << "name: " << name << endl;
    cout << "ID: " << id << endl;
    cout << "gender: " << gender << endl;
    cout << "birthday" << endl;
    birthday.displayDate();
}

//Person
class Student : virtual public Person {
protected:
    char studentNo[10];
    char schoolName[20];
    char classIn[20];
public:
    Student();

    Student(char *sname, char *sid, char *sgender, int y, int m, int d, char *sno, char *sschool, char *sclass);

    ~Student() {};

    void setStudentNo(char *sno) { strcpy(studentNo, sno); }

    char *getStudentNo() { return studentNo; }

    void setschoolName(char *sschool) { strcpy(schoolName, sschool); }

    char *getschoolName() { return schoolName; }

    void setclassIn(char *sclass) { strcpy(classIn, sclass); }

    char *getclassIn() { return classIn; }

    virtual void inputData();//输入数据
    virtual void displayDetails(); //显示数据
};

Student::Student() : Person() {
    strcpy(studentNo, "001");
    strcpy(schoolName, "null_schoolName");
    strcpy(classIn, "null_classIn");
}

Student::Student(char *sname, char *sid, char *sgender, int y, int m, int d, char *sno, char *sschool, char *
sclass) : Person(sname, sid, sgender, y, m, d) {
    strcpy(studentNo, sno);
    strcpy(studentNo, sno);
    strcpy(classIn, sclass);
}

void Student::inputData() {
    Person::inputData();
    cout << "StudentNo: ";
    cin >> studentNo;
    cout << "school: ";
    cin >> schoolName;
    cout << "classIn: ";
    cin >> classIn;
}

void Student::displayDetails() {
    Person::displayDetails();
    cout << "studentNo: " << studentNo << endl;
    cout << "schoolName: " << schoolName << endl;
    cout << "classIn: " << classIn << endl;
}

//Student
class Teacher : virtual public Person {
protected:
    char TeacherNo[5];
    char SchoolName[20];
    char Department[20];
public:
    Teacher();

    Teacher(char *sname, char *sid, char *sgender, int y, int m, int d, char *steacherNo, char *sschool, char *
    sdepartment);

    ~Teacher() {};

    void setTeacherNo(char *sno) { strcpy(TeacherNo, sno); }

    char *getteacherNo() { return TeacherNo; }

    void setSchoolName(char *sschool) { strcpy(SchoolName, sschool); }

    char *getSchoolName() { return SchoolName; }

    void setDepartment(char *sdepartment) { strcpy(Department, sdepartment); }

    char *getDepartment() { return Department; }

    virtual void inputData();//输入数据
    virtual void displayDetails(); //显示数据
};

Teacher::Teacher() : Person() {
    strcpy(TeacherNo, "001");
    strcpy(SchoolName, "null_school");
    strcpy(Department, "null_department");
}

Teacher::Teacher(char *sname, char *sid, char *sgender, int y, int m, int d, char *steacherNo, char *sschool, char *
sdepartment) : Person(sname, sid, sgender, y, m, d) {
    strcpy(TeacherNo, steacherNo);
    strcpy(SchoolName, sschool);
    strcpy(Department, sdepartment);
}

void Teacher::inputData() {
    Person::inputData();
    cout << "teacherNo: ";
    cin >> TeacherNo;
    cout << "School: ";
    cin >> SchoolName;
    cout << "Department";
    cin >> Department;
}

void Teacher::displayDetails() {
    Person::displayDetails();
    cout << "TeahcerNo: " << TeacherNo << endl;
    cout << "School: " << SchoolName << endl;
    cout << "Deartment: " << Department << endl;
}

//Teacher
class UnderGraduate : virtual public Student {
protected:
    char major[20];
public:
    UnderGraduate();

    UnderGraduate(char *sname, char *sid, char *sgender, int y, int m, int d, char *sno, char *sschool, char *sclass,
                  char *
                  smajor);

    ~UnderGraduate() {};

    void setMajor(char *smajor) { strcpy(major, smajor); }

    char *getMajor() { return major; }

    virtual void inputData();

    virtual void displayDetails();
};

UnderGraduate::UnderGraduate() : Student() {
    strcpy(major, "null_Major");
}

UnderGraduate::UnderGraduate(char *sname, char *sid, char *sgender, int y, int m, int d, char *sno, char *sschool,
                             char *
                             sclass, char *smajor) : Student(sname, sid, sgender, y, m, d, sno, sschool, sclass) {
    strcpy(major, smajor);
}

void UnderGraduate::inputData() {
    Student::inputData();
    cout << "Major: ";
    cin >> major;
}

void UnderGraduate::displayDetails() {
    Student::displayDetails();
    cout << "Major: " << major << endl;
}

//UnderGraduate
class UnderGraduateManager {
private:
    //warning: default member initializer for non-static data member is a C++11 extension
    //变量未在此范围内声明
//    int top = 0;
    UnderGraduate undergraduates[100];
public:
    int top = 0;
    UnderGraduateManager();//读取文件
    int queryByNo(string sno);
    void clearStudent();
    int addStudent(UnderGraduate s);
    int modifyStudent(string sno);
    int deleteStudent(string sno);
    int queryStudent(string sno);
    void displayAll();
    void dataManage();
    void dataSave();
    void dataRead();
    ~UnderGraduateManager();//写入文件
};

UnderGraduateManager::UnderGraduateManager() {
    dataRead();
}

int UnderGraduateManager::queryByNo(string sno) { // 查找，有：i， 无： -1
    for (int i = 0; i <= top; i++)
        if (undergraduates[i].getStudentNo() == sno)
            return i;
    return -1;
}

void UnderGraduateManager::clearStudent() {//停止检索最后一个元素，相当于删除元素
    top = -1;
}

int UnderGraduateManager::addStudent(UnderGraduate s) {//若不存在该元素（return -1），则元素数+1，保存至文件
    int p = queryByNo(s.getStudentNo());
    if (p == -1) {
        top++;
        undergraduates[top] = s;
        dataSave();
        return 1;
    } else {
        cout << "existed!" << endl;
        return 0;
    }
}

int UnderGraduateManager::modifyStudent(string sno) {//若存在该元素（return ！= -1），则修改属性，保存至文件
    int p = queryByNo(sno);
    if (p == -1) {
        cout << "Not exist!" << endl;
        return 0;
    } else {
        cout << "please input new detials" << endl;
        undergraduates[p].inputData();
        dataSave();
        return 1;
    }
}

int UnderGraduateManager::deleteStudent(string sno) {//删除元素，其他元素前移
    int p = queryByNo(sno);
    if (p == -1) {
        cout << "Not exist!" << endl;
        return 0;
    } else {
        for (int i = 0; i < top; i++)
            undergraduates[i] = undergraduates[i + 1];
        top--;
        cout << "Deleted!" << endl;
        dataSave();
        return 1;
    }
}

int UnderGraduateManager::queryStudent(string sno) {
    int p = queryByNo(sno);
    if (p == -1) {
        cout << "Not exist!" << endl;
        return 0;
    } else {
        cout << "exist!" << endl;
        undergraduates[p].displayDetails();
        return 1;
    }
}

void UnderGraduateManager::displayAll() {
    for (int i = 0; i <= top; i++) {
        cout << "The NO." << i << "student's info:" << endl;
        undergraduates[i].displayDetails();
    }
}

UnderGraduateManager::~UnderGraduateManager() {
    dataSave();
}

void UnderGraduateManager::dataManage() {
    int choice = 1;
    string sstudentNo;
    UnderGraduate s;
    while (choice != 0) {
        cout << "**********************************" << endl;
        cout << "\t\t undergraduate maintainace\n";
        cout << "**********************************" << endl;
        cout << "\n\t\t 1.Add";
        cout << "\n\t\t 2.Modify";
        cout << "\n\t\t 3.Delete";
        cout << "\n\t\t 4.Search";
        cout << "\n\t\t 5.Show";
        cout << "\n\t\t 6.ClearAll";
        cout << "\n\t\t 0.exit";
        cout << endl;
        cout << "**********************************" << endl;
        cout << "Please take your choice: ";
        cin >> choice;
        switch (choice) {
            case 1:
                s.inputData();
                addStudent(s);
                break;
            case 2:
                cout << " Please input student's number:";
                cin >> sstudentNo;
                modifyStudent(sstudentNo);
                break;
            case 3:
                cout << " Please input student's number:";
                cin >> sstudentNo;
                deleteStudent(sstudentNo);
                break;
            case 4:
                cout << " Please input student's number:";
                cin >> sstudentNo;
                queryStudent(sstudentNo);
                break;
            case 5:
                displayAll();
                break;
            case 6:
                clearStudent();
            default:
                break;
        }
    }
}

void UnderGraduateManager::dataSave() {
    fstream file("D:\\ProgramData\\vs\\design\\UGM.docx", ios::out);// !
    for (int i = 0; i <= top; i++) {
        file.write((char *) &undergraduates[i], sizeof(undergraduates[i]));
    }
    file.close();
}

void UnderGraduateManager::dataRead() {
    UnderGraduate s;
    top--;
    fstream file("D:\\ProgramData\\vs\\design\\UGM.docx", ios::in); // !
    while (1) {
        file.read((char *) &s, sizeof(s));
        if (!file) break;
        top++;
        undergraduates[top] = s;
    }
    file.close();
}

//UnderGraduateManager
class Graduate : public Student {
protected:
    char direction[20];
    char advisorName[20];
public:
    Graduate();

    Graduate(char *sname, char *sid, char *sgender, int y, int m, int d, char *sno, char *sschool, char *sclass, char *
    sdirection, char *sadvisor);

    ~Graduate() {};

    void setDirction(char *sdirection) { strcpy(direction, sdirection); }

    char *getDirction() { return direction; }

    void setAdvisorName(char *sadvisor) { strcpy(advisorName, sadvisor); }

    char *getAdvisorName() { return advisorName; }

    virtual void inputData();

    virtual void displayDetails();
};

Graduate::Graduate() : Student() {
    strcpy(advisorName, "null_advisorName");
    strcpy(direction, "null_direction");
}

Graduate::Graduate(char *sname, char *sid, char *sgender, int y, int m, int d, char *sno, char *sschool, char *sclass,
                   char *sdirection, char *sadvisor) : Student(sname, sid, sgender, y, m, d, sno, sschool, sclass) {
    strcpy(advisorName, sdirection);
    strcpy(direction, sadvisor);
}

void Graduate::inputData() {
    Student::inputData();
    cout << "direction: ";
    cin >> direction;
    cout << "advisorName: ";
    cin >> advisorName;
}

void Graduate::displayDetails() {
    Student::displayDetails();
    cout << "Direction: " << direction << endl;
    cout << "Advisor Name: " << advisorName << endl;
}

class GraduateManager {
private:
    int top = 0;
    Graduate graduates[100];
public:
    GraduateManager();

    int queryByNo(string sno);

    void clearStudent();

    int addStudent(Graduate s);

    int modifyStudent(string sno);

    int deleteStudent(string sno);

    int queryStudent(string sno);

    void displayAll();

    void dataManage();

    void dataSave();

    void dataRead();

    ~GraduateManager();//写入文件
};

GraduateManager::GraduateManager() {
    dataRead();
}

int GraduateManager::queryByNo(string sno) {
    for (int i = 0; i <= top; i++) {
        if (graduates[i].getStudentNo() == sno)
            return i;
    }
    return -1;
}

void GraduateManager::clearStudent() {
    top--;
}

int GraduateManager::addStudent(Graduate s) {
    int p = queryByNo(s.getStudentNo());
    if (p == -1) {
        top++;
        graduates[top] = s;
        dataSave();
        return 1;
    } else {
        cout << "existed!" << endl;
        return 0;
    }
}

int GraduateManager::modifyStudent(string sno) {
    int p = queryByNo(sno);
    if (p == -1) {
        cout << "Not exist!" << endl;
        return 0;
    } else {
        cout << "Please input new info" << endl;
        graduates[p].inputData();
        dataSave();
        return 1;
    }
}

int GraduateManager::deleteStudent(string sno) {
    int p = queryByNo(sno);
    if (p == -1) {
        cout << "Not exist!" << endl;
        return 0;
    } else {
        for (int i = p; i < top; i++)
            graduates[i] = graduates[i + 1];
        top--;
        cout << "Delete！" << endl;
        dataSave();
        return 1;
    }
}

int GraduateManager::queryStudent(string sno) {
    int p = queryByNo(sno);
    if (p == -1) {
        cout << "Not exist!" << endl;
        return 0;
    } else {
        cout << "Exist!" << endl;
        graduates[p].displayDetails();
        return 1;
    }
}

void GraduateManager::displayAll() {
    for (int i = 0; i <= top; i++) {
        cout << "The NO." << i << " student's info:" << endl;
        graduates[i].displayDetails();
    }
}

GraduateManager::~GraduateManager() {
    dataSave();
}

void GraduateManager::dataManage() {
    int choice = 1;
    string sstudentNo;
    Graduate s;

    while (choice != 0) {
        cout << "**********************************" << endl;
        cout << "\t\t Graduate maintainace\n";
        cout << "**********************************" << endl;
        cout << "\n\t\t 1.Add";
        cout << "\n\t\t 2.Modify";
        cout << "\n\t\t 3.Delete";
        cout << "\n\t\t 4.Search";
        cout << "\n\t\t 5.Show";
        cout << "\n\t\t 6.ClearAll";
        cout << "\n\t\t 0.exit";
        cout << endl;
        cout << "**********************************" << endl;
        cout << "Please take your choice: ";
        cin >> choice;
        switch (choice) {
            case 1:
                s.inputData();
                addStudent(s);
                break;
            case 2:
                cout << " Please input student's number:";
                cin >> sstudentNo;
                modifyStudent(sstudentNo);
                break;
            case 3:
                cout << " Please input student's number:";
                cin >> sstudentNo;
                deleteStudent(sstudentNo);
                break;
            case 4:
                cout << " Please input student's number:";
                cin >> sstudentNo;
                queryStudent(sstudentNo);
                break;
            case 5:
                displayAll();
                break;
            case 6:
                clearStudent();
            default:
                break;
        }
    }
}

void GraduateManager::dataSave() {
    fstream file("D:\\ProgramData\\vs\\design\\GM.docx", ios::out);// !
    for (int i = 0; i <= top; i++) {
        file.write((char *) &graduates[i], sizeof(graduates[i]));
    }
    file.close();
}

void GraduateManager::dataRead() {
    Graduate s;
    top--;
    fstream file("D:\\ProgramData\\vs\\design\\GM.docx", ios::in); // !
    while (1) {
        file.read((char *) &s, sizeof(s));
        if (!file) break;
        top++;
        graduates[top] = s;
    }
    file.close();
}

class TA : public Graduate, public Teacher {
public:
    TA();

    TA(char *sname, char *sid, char *sgender, int y, int m, int d, char *steacherNo, char *sschool, char *sdepartment,
       char *sno, char *sclass, char *sdirection, char *sadvisorName);

    ~TA() {};

    virtual void inputData();

    virtual void displayDetails();
};

TA::TA() : Teacher(), Graduate() {

}

TA::TA(char *sname, char *sid, char *sgender, int y, int m, int d, char *steacherNo, char *sschool, char *sdepartment,
       char *sno, char *sclass, char *sdirection, char *sadvisorName) : Person(sname, sid, sgender, y, m, d) {

}

void TA::inputData() {
    char teacherNo[5];
    char department[20];
    Graduate::inputData();
    cout << "teacherNo:";
    cin >> teacherNo;
    cout << "department: ";
    cin >> department;
    Teacher::setId(teacherNo);
    Teacher::setDepartment(department);
}

void TA::displayDetails() {
    Graduate::displayDetails();
    Teacher::displayDetails();
}

class TAManager {
private:
    int top;
    TA tas[100];
public:
    TAManager();

    int queryByNo(string sno);

    void clearStudent();

    int addStudent(TA s);

    int modifyStudent(string sno);

    int deleteStudent(string sno);

    int queryStudent(string sno);

    void displayAll();

    void dataManage();

    void dataSave();

    void dataRead();

    ~TAManager();
};

TAManager::TAManager() {
    dataRead();
}

int TAManager::queryByNo(string sno) {
    for (int i = 0; i <= top; i++)
        if (tas[i].getStudentNo() == sno)
            return i;
    return -1;
}

void TAManager::clearStudent() {
    top--;
}

int TAManager::addStudent(TA s) {
    int p = queryByNo(s.getStudentNo());
    if (p == -1) {
        top++;
        tas[top] = s;
        dataSave();
        return 1;
    } else {
        cout << "Existed!" << endl;
        return 0;
    }
}

int TAManager::modifyStudent(string sno) {
    int p = queryByNo(sno);
    if (p == -1) {
        cout << "Not exist!" << endl;
        return 0;
    } else {
        cout << "Please input new info: " << endl;
        tas[p].inputData();
        dataSave();
        return 1;
    }
}

int TAManager::deleteStudent(string sno) {
    int p = queryByNo(sno);
    if (p == -1) {
        cout << "Not exist!" << endl;
        return 0;
    } else {
        for (int i = p; i < top; i++)
            tas[i] = tas[i + 1];
        top--;
        cout << "Deleted!" << endl;
        dataSave();
        return 1;
    }
}

int TAManager::queryStudent(string sno) {
    int p = queryByNo(sno);
    if (p == -1) {
        cout << "Not exist!" << endl;
        return 0;
    } else {
        cout << "Exsit!" << endl;
        tas[p].displayDetails();
        return 1;
    }
}

void TAManager::displayAll() {
    for (int i = 1; i <= top; i++) {
        cout << "The NO." << i << " student's info" << endl;
        tas[i].displayDetails();
    }
}

TAManager::~TAManager() {
    dataSave();
}

void TAManager::dataManage() {
    int choice = 1;
    string sstudentNo;
    TA s;

    while (choice != 0) {
        cout << "**********************************" << endl;
        cout << "\t\t Graduate maintainace\n";
        cout << "**********************************" << endl;
        cout << "\n\t\t 1.Add";
        cout << "\n\t\t 2.Modify";
        cout << "\n\t\t 3.Delete";
        cout << "\n\t\t 4.Search";
        cout << "\n\t\t 5.Show";
        cout << "\n\t\t 6.ClearAll";
        cout << "\n\t\t 0.exit";
        cout << endl;
        cout << "**********************************" << endl;
        cout << "Please take your choice: ";
        cin >> choice;
        switch (choice) {
            case 1:
                s.inputData();
                addStudent(s);
                break;
            case 2:
                cout << " Please input student's number:";
                cin >> sstudentNo;
                modifyStudent(sstudentNo);
                break;
            case 3:
                cout << " Please input student's number:";
                cin >> sstudentNo;
                deleteStudent(sstudentNo);
                break;
            case 4:
                cout << " Please input student's number:";
                cin >> sstudentNo;
                queryStudent(sstudentNo);
                break;
            case 5:
                displayAll();
                break;
            case 6:
                clearStudent();
            default:
                break;
        }
    }
}

void TAManager::dataSave() {
    fstream file("D:\\ProgramData\\vs\\design\\TA.docx", ios::out);
    for (int i = 0; i <= top; i++)
        file.write((char *) &tas[i], sizeof(tas[i]));
    file.close();
}

void TAManager::dataRead() {
    TA s;
    top--;
    fstream file("D:\\ProgramData\\vs\\design\\TA.docx", ios::in);
    while (1) {
        file.read((char *) &s, sizeof(s));
        if (!file) break;
        top++;
        tas[top] = s;
    }
    file.close();
}

int main(int argc, const char *argv[]) {
    int choiceN;
    UnderGraduateManager unMan;
    GraduateManager grMan;
    TAManager TAMan;
    cout << "********************************************************" << endl;
    cout << "*|*| |*|*" << endl;
    cout << "*|*| 欢迎您使用学生管理系统 |*|*" << endl;
    cout << "*|*| |*|*" << endl;
    cout << "********************************************************" << endl;
    do {
        cout << "<---------------------------------------------------->" << endl;
        cout << " \n \t\t 1:Undergraduate Manage ";
        cout << " \n \t\t 2:Graduate Manage ";
        cout << " \n \t\t 3.TA Manage ";
        cout << " \n \t\t 0:Exit ";
        cout << endl;
        cout << "<---------------------------------------------------->" << endl;
        cout << "Please choose：" << endl;
        cin >> choiceN;
        switch (choiceN) {
            case 1:
                unMan.dataManage();
                break;
            case 2:
                grMan.dataManage();
                break;
            case 3:
                TAMan.dataManage();
                break;
            default:
                break;
        }
    } while (choiceN != 0);
    cout << " **********************************************************" << endl;
    cout << "*|*| 感谢使用学生管理系统 |*|*" << endl;
    cout << " **********************************************************\a" << endl;
    system("pause");
}