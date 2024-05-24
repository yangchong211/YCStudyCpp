//
// Created by 杨充 on 2023/6/14.
//

#include <iostream>
using namespace std;

//C++ 多态
//多态按字面的意思就是多种形态。当类之间存在层次结构，并且类之间是通过继承关联时，就会用到多态。


void test1();
void test2();

int main() {
    test1();
    test2();
    return 0;
}

class Shape {
protected:
    int width, height;
public:
    Shape(int a = 0, int b = 0) {
        width = a;
        height = b;
    }
    int area() {
        cout << "Parent class area :" << endl;
        return 0;
    }
};

//C++ 多态
//多态按字面的意思就是多种形态。当类之间存在层次结构，并且类之间是通过继承关联时，就会用到多态。
class RectTangle : public Shape{
public:
    RectTangle(int a=0, int b=0): Shape(a,b){

    }
    int area(){
        int area;
        area = (width * height);
        cout << "Rectangle class area :" << area <<endl;
        return area;
    }
};

class Triangle: public Shape{
public:
    Triangle( int a=0, int b=0):Shape(a, b) { }
    int area (){
        int area;
        area = (width * height / 2);
        cout << "Triangle class area :" << area << endl;
        return area;
    }
};


void test1(){
    Shape *shape;
    RectTangle rec(10,7);
    Triangle triangle(10,5);
    //存储矩形的地址
    shape = &rec;
    shape -> area();

    shape = &triangle;
    shape ->area();
}


void test2(){

}