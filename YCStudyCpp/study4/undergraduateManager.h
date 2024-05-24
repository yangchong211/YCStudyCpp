#ifndef UNDERGRADUATE_MANAGER_H
#define UNDERGRADUATE_MANAGER_H

#include <iostream>
#include <string>
#include <fstream>
#include "undergraduate.h"

using namespace std;

/* Define a Class : UndergraduateManager 本科生管理类*/
class UndergraduateManager {
private:
    int top; //记录指针
    Undergraduate undergraduates[100]; //本科生记录
public:
    UndergraduateManager();//构造函数,将Undergraduate.txt读到undergraduates[]中
    int queryByNo(string sno);//按本科生号查找 //找到：返回数组下标//没找到：返回-1
    void clearStudent();  //删除所有本科生信息
    int addStudent(Undergraduate s); //添加本科生,需要先查找是否存在
    int modifyStudent(string sno);  //修改学生信息 ,需要先查找是否存在
    int deleteStudent(string sno);//删除本科生，删除前先查找其是否存在
    int queryStudent(string sno);//查找本科生,查到则显示,否则提示未查到
    void displayAll();//输出所有本科生信息
    void dataManage(); //本科生库维护
    void dataSave();

    void dataRead();

    ~UndergraduateManager();//析构函数,将undergraduates[]写入Undergraduate.txt文件中
};

//构造函数,将Undergraduate.txt读到undergraduates[]中
UndergraduateManager::UndergraduateManager() {
    dataRead();
}

//按本科生号查找
//找到：返回数组下标
//没找到：返回-1
int UndergraduateManager::queryByNo(string sno) {
    for (int i = 0; i <= top; i++)
        if (undergraduates[i].getStudentNo() == sno)
            return i;
    return -1;
}

//删除所有本科生信息
void UndergraduateManager::clearStudent() {
    top = -1;
}

//添加本科生,需要先查找是否存在
int UndergraduateManager::addStudent(Undergraduate s) {
    int p = queryByNo(s.getStudentNo());
    if (p == -1) {
        top++;
        undergraduates[top] = s;
        dataSave();//保存
        return 1;
    } else {
        cout << "--------->此学生已经存在 !<----------" << endl << endl;
        return 0;
    }
}

//修改科生，删除前先查找其是否存在
int UndergraduateManager::modifyStudent(string sno) {
    int p = queryByNo(sno);
    if (p == -1) {
        cout << "--------->此学生不存在 !<----------" << endl << endl;
        return 0;
    } else {
        cout << "请输入该生的新信息: " << endl << endl;
        undergraduates[p].inputData();
        dataSave();//保存
        return 1;
    }

}

//删除本科生，删除前先查找其是否存在
int UndergraduateManager::deleteStudent(string sno) {
    int p = queryByNo(sno);
    if (p == -1) {
        cout << "--------->此学生不存在 !<----------" << endl << endl;
        return 0;
    } else {
        for (int i = p; i < top; i++)
            undergraduates[i] = undergraduates[i + 1];
        top--;
        cout << "--------->删除完成!<----------" << endl << endl;
        dataSave();//保存
        return 1;
    }
}

//查找科生
int UndergraduateManager::queryStudent(string sno) {
    int p = queryByNo(sno);
    if (p == -1) {
        cout << "--------->此学生不存在 !<----------" << endl << endl;
        return 0;
    } else {
        cout << "--------->此学生存在:<----------" << endl << endl;
        undergraduates[p].displayDetails();
        return 1;
    }
}

//输出所有本科生信息
void UndergraduateManager::displayAll() {
    for (int i = 0; i <= top; i++) {
        cout << "--------第" << i << "个学生情况----------" << endl << endl;
        undergraduates[i].displayDetails();

    }
}

//析构函数,将undergraduates[]写入Undergraduate.txt文件中
UndergraduateManager::~UndergraduateManager() {
    dataSave();
}

void UndergraduateManager::dataManage() {
    int choice = 1;
    string sstudentNo;
    Undergraduate s;

    while (choice != 0) {
        cout << "********************************************" << endl;
        cout << "\t\t本科生维护\n";
        cout << "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^" << endl;
        cout << "\n \t\t 1:新增 ";
        cout << "\n \t\t 2:修改 ";
        cout << "\n \t\t 3:删除 ";
        cout << "\n \t\t 4:查找 ";
        cout << "\n \t\t 5:显示 ";
        cout << "\n \t\t 6:全部删除 ";
        cout << "\n \t\t 0:退出 ";
        cout << endl;
        cout << "*********************************************" << endl;
        cout << "请选择：" << endl;
        cin >> choice;
        switch (choice) {
            case 1:
                s.inputData();
                addStudent(s);
                break;
            case 2:
                cout << " 请输入学号:";
                cin >> sstudentNo;
                modifyStudent(sstudentNo);
                break;
            case 3:
                cout << " 请输入学号:";
                cin >> sstudentNo;
                deleteStudent(sstudentNo);
                break;
            case 4:
                cout << " 请输入学号:";
                cin >> sstudentNo;
                queryStudent(sstudentNo);
                break;
            case 5:
                displayAll();
                break;
            case 6:
                clearStudent();
                break;
            default:
                break;
        }
    }
}


//存储资料函数,将read[]写入Undergraduate.txt文件中
void UndergraduateManager::dataSave()
{
    fstream file("C:\\Undergraduate.dat", ios::out);
    for (int i = 0; i <= top; i++)
        file.write((char *) &undergraduates[i], sizeof(undergraduates[i]));
    file.close();
}

//构造函数,将Undergraduate.txt读到read[]中
void UndergraduateManager::dataRead() {
    Undergraduate s;
    top = -1;
    fstream file("C:\\Undergraduate.dat", ios::in);
    while (1) {
        file.read((char *) &s, sizeof(s));
        if (!file) break;
        top++;
        undergraduates[top] = s;
    }
    file.close();
}

#endif //UNDERGRADUATE_MANAGER_H