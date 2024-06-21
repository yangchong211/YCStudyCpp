#include <cstdlib>
#include <iostream>
#include "date.h"
#include "student.h"
#include "teacher.h"
#include "undergraduate.h"
#include "graduate.h"
#include "ta.h"
#include "undergraduateManager.h"
#include"taManger.h"

using namespace std;

int main(int argc, char *argv[]) {
    int choiceN;
    UndergraduateManager unMan;
    GradateManager unman1;
    taManager unman2;
    cout << "********************************************************" << endl;
    cout << "*|*|                                                |*|*" << endl;
    cout << "*|*|              欢迎您使用学生管理系统            |*|*" << endl;
    cout << "*|*|                                                |*|*" << endl;
    cout << "********************************************************" << endl;
    do {
        cout << "<---------------------------------------------------->" << endl;
        cout << " \n \t\t 1:本科生管理 ";
        cout << " \n \t\t 2:研究生管理  ";
        cout << " \n \t\t 3.助教博士生管理 ";
        cout << " \n \t\t 0:离开    ";
        cout << endl;
        cout << "<---------------------------------------------------->" << endl;
        cout << "请选择：" << endl;
        cin >> choiceN;
        switch (choiceN) {
            case 1:
                unMan.dataManage();
                break;
            case 2:
                unman1.dataManage();
                break;
            case 3:
                unman2.dataManage();
                break;
            default:
                break;
        }
    } while (choiceN != 0);
    cout << " **********************************************************" << endl;
    cout << "*|*|               感谢使用学生管理系统                  |*|*" << endl;
    cout << " **********************************************************\a" << endl;
    system("pause");
}