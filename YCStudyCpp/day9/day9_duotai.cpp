//
// Created by 杨充 on 2023/6/14.
//

#include <iostream>
using namespace std;

//C++ 多态 1.必须要继承关联，2.重写父类方法
//多态按字面的意思就是多种形态。当类之间存在层次结构，并且类之间是通过继承关联时，就会用到多态。
//9.3.1.1 什么是多态
void test9_3_1_1();
//9.3.1.2 虚函数概述
void test9_3_1_2();
//9.3.1.3 利用虚函数实现动态绑定
void test9_3_1_3();
//9.3.1.4 虚继承
void test9_3_1_4();


int main() {
    test9_3_1_1();
    test9_3_1_2();
    test9_3_1_3();
    test9_3_1_4();
    return 0;
}

class Shape {
protected:
    int width;
    int height;
public:
    Shape();
    Shape(int a, int b) {
        width = a;
        height = b;
    }
    int area() {
        cout << "Parent class area :" << endl;
        return 0;
    }
    virtual void draw() {
        // 基类虚函数的默认实现
        cout << "Drawing a shape." << endl;
    }
};

//C++ 多态
//多态按字面的意思就是多种形态。当类之间存在层次结构，并且类之间是通过继承关联时，就会用到多态。
class RectTangle : public Shape {
public:
    //实现
    RectTangle(int a, int b) : Shape(a, b) {}
    int area() {
        int area;
        area = (width * height / 2);
        cout << "Triangle class area :" << area << endl;
        return area;
    }
};


class Triangle : public Shape {
public:
    Triangle(int a = 0, int b = 0) : Shape(a, b) {}
    int area() {
        int area;
        area = (width * height / 2);
        cout << "Triangle class area :" << area << endl;
        return area;
    }
};


//9.3.1.1 什么是多态
void test9_3_1_1() {
    //多态按字面的意思就是多种形态。当类之间存在层次结构，并且类之间是通过继承关联时，就会用到多态。
    //C++ 多态意味着调用成员函数时，会根据调用函数的对象的类型来执行不同的函数。
    Shape *shape;
    RectTangle rec(10,7);
    Triangle triangle(10,5);
    // 存储矩形的地址
    shape = &rec;
    // 调用矩形的求面积函数 area
    shape->area();
    // 存储三角形的地址
    shape = &triangle;
    // 调用三角形的求面积函数 area
    shape->area();
}


class Base {
public:
    //在基类中声明虚函数：在基类中将要被派生类重写的函数声明为虚函数。使用virtual关键字来标识虚函数。例如：
    virtual void foo() {
        cout << "基类虚函数的实现" << endl;
    }
};

class Derived : public Base {
public:
    //在派生类中重写虚函数：在派生类中重写基类的虚函数，使用相同的函数签名。使用override关键字来明确表示重写基类的虚函数。例如：
    void foo() override {
        cout << "派生类虚函数的实现" << endl;
    }
};


//9.3.1.2 虚函数概述
void test9_3_1_2() {
    cout << "9.3.1.2 虚函数概述" << endl;
    //虚函数 是在基类中使用关键字 virtual 声明的函数。在派生类中重新定义基类中定义的虚函数时，会告诉编译器不要静态链接到该函数。
    //我们想要的是在程序中任意点可以根据所调用的对象类型来选择调用的函数，这种操作被称为动态链接，或后期绑定。

    //通过基类指针或引用调用虚函数：通过基类指针或引用指向派生类对象，并调用虚函数。在运行时，将根据实际对象的类型来调用相应的虚函数实现。例如：
    //在示例中，通过将基类指针指向派生类对象，并调用虚函数foo()，实际上会调用派生类Derived中的虚函数实现。
    //通过虚函数和多态，可以实现基于对象的动态行为，提高代码的灵活性和可扩展性。
    //在运行时，根据实际对象的类型，动态地选择调用相应的函数实现。这是C++中面向对象编程的重要特性之一。
    Base *base = new Derived();
    base->foo();
    //派生类虚函数的实现
}

class Circle : public Shape {
public:
    void draw() override {
        // 派生类虚函数的实现
        cout << "Drawing a circle." << endl;
    }
};

class Rectangle : public Shape {
public:
    void draw() override {
        // 派生类虚函数的实现
        cout << "Drawing a rectangle." << endl;
    }
};

//9.3.1.3 利用虚函数实现动态绑定
void test9_3_1_3() {
    //通过利用虚函数实现动态绑定，可以在运行时根据对象的实际类型来调用相应的函数，实现多态的效果。
    //这提供了更灵活和可扩展的代码结构，使得程序能够适应不同类型的对象并执行相应的操作。
    cout << "9.3.1.3 利用虚函数实现动态绑定" << endl;
    Shape *shape;
    Circle circle;
    Rectangle rectangle;

    //示例中，基类Shape中的draw()函数被声明为虚函数。派生类Circle和Rectangle分别重写了基类的虚函数。
    //由于draw()函数是虚函数，因此在运行时会根据实际对象的类型来动态绑定，选择调用相应的派生类的函数实现。


    // 动态绑定，调用Circle类的draw()函数
    shape = &circle;
    shape->draw();

    // 动态绑定，调用Rectangle类的draw()函数
    shape = &rectangle;
    shape->draw();
}

class Derived1 : virtual public Base {
public:
    // 派生类1的成员和函数
};

class Derived2 : virtual public Base {
public:
    // 派生类2的成员和函数
};

class Derived3 : public Derived1, public Derived2 {
public:
    // 派生类3的成员和函数
};
//9.3.1.4 虚继承
void test9_3_1_4() {
    //示例中，Derived1和Derived2都使用了虚继承来继承自Base类。而Derived3通过多继承同时继承了Derived1和Derived2。
    //使用虚继承后，当创建Derived3的对象时，只会有一个Base类的实例，而不会出现多个实例。这样可以避免菱形继承中的冗余数据和函数的问题。
}





