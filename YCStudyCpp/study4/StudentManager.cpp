//
// Created by 杨充 on 2024/1/16.
//

#include "StudentManager.h"
using namespace std;

class UnderGraduateManager{

public:
    void dataManage();
};

class GraduateManager{

};

class TAManager{

};


int main(int argc, const char *argv[]) {
    initMenu();
    UnderGraduateManager unMan;
    GraduateManager grMan;
    TAManager TAMan;
    int choiceN;
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
    initOver();
}

void initOver() {
    cout << " **********************************************************" << endl;
    cout << "*|*| 感谢使用学生管理系统 |*|*" << endl;
    cout << " **********************************************************\a" << endl;
}


void initMenu() {
    cout << "********************************************************" << endl;
    cout << "*|*| 欢迎您使用学生管理系统 |*|*" << endl;
    cout << "********************************************************" << endl;
}
