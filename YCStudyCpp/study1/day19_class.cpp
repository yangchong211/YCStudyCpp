//
// Created by 杨充 on 2023/6/9.
//
#include <iostream>
using namespace std;

//C++ 类定义
//定义一个类需要使用关键字 class，然后指定类的名称，并类的主体是包含在一对花括号中，主体包含类的成员变量和成员函数。

class Student{
public:
    int age;
    char name[50];
    double height;
private:
    char parent[20];
};

//定义 C++ 对象
void test1();
//C++ 类成员函数
void test2();

//定义 C++ 对象
//类提供了对象的蓝图，所以基本上，对象是根据类来创建的。声明类的对象，就像声明基本类型的变量一样。下面的语句声明了类 Box 的两个对象：
int main(){
    test1();
    test2();
    return 0;
}

void test1(){
    //定义 C++ 对象
    //跟java不一样，不需要new就定义了对象
    Student student;
    cout << "学生age : " << student.age <<endl;
    cout << "学生height : " << student.height <<endl;
    //学生age : 0
    //学生height : 0


    //访问数据成员
    student.age = 28;
    //student.name = "yc";
    student.height = 172;
    cout << "学生age : " << student.age <<endl;
    cout << "学生height : " << student.height <<endl;
    cout << "\n";
    //学生age : 28
    //学生height : 172
}


//C++ 类成员函数
class Box {
public:
    double length;      // 长度
    double breadth;     // 宽度
    double height;      // 高度
    double getVolume(void) {
        return length * breadth * height;
    }
    void setLength( double len );
};

void Box::setLength(double len){
    length = len;
}

void test2(){
    Box box;
    box.setLength(15.0);
    box.breadth = 5;
    box.height = 6;
    double volume = box.getVolume();
    cout << "Box1 的体积：" << volume <<endl;
}