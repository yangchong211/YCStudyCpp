//
// Created by 杨充 on 2023/6/16.
//


#include <iostream>
using namespace std;

//g++ -std=c++11
//9.4.1.1 什么是抽象类
void test9_4_1_1();
//9.4.1.2 抽象类的实例
void test9_4_1_2();
//9.4.1.3 实现抽象类中的成员函数
void test9_4_1_3();
//9.4.2.1 什么是数据抽象
void test9_4_2_1();
//9.4.2.2 访问标签强制抽象
void test9_4_2_2();
//9.4.2.3 数据抽象的实例
void test9_4_2_3();
//9.4.2.5 数据抽象的好处
void test9_4_2_5();


int main(){
    test9_4_1_1();
    test9_4_1_2();
    test9_4_1_3();
    test9_4_2_1();
    test9_4_2_2();
    test9_4_2_3();
    test9_4_2_5();
    return 0;
}

class Box{
public:
    // 纯虚函数
    virtual double getVolume() = 0;
private:
    double length;      // 长度
    double breadth;     // 宽度
    double height;      // 高度
};

class Box1 : public Box{
public:
    double getVolume() override{
        return 0;
    }
};

//9.4.1.1 什么是抽象类
void test9_4_1_1() {
    //接口描述了类的行为和功能，而不需要完成类的特定实现。
    //C++ 接口是使用抽象类来实现的，抽象类与数据抽象互不混淆，数据抽象是一个把实现细节与相关的数据分离开的概念。
    //如果类中至少有一个函数被声明为纯虚函数，则这个类就是抽象类。纯虚函数是通过在声明中使用 "= 0" 来指定的，如下所示：

    //设计抽象类（通常称为 ABC）的目的，是为了给其他类提供一个可以继承的适当的基类。
    //抽象类不能被用于实例化对象，它只能作为接口使用。如果试图实例化一个抽象类的对象，会导致编译错误。

    //因此，如果一个 ABC 的子类需要被实例化，则必须实现每个纯虚函数，这也意味着 C++ 支持使用 ABC 声明接口。
    //如果没有在派生类中重写纯虚函数，就尝试实例化该类的对象，会导致编译错误。
    //可用于实例化对象的类被称为具体类。

//    Box box = new Box();
    new Box1();
}


class Shape {
public:
    virtual int getArea() = 0;
    void setWidth(int w) {
        width = w;
    }
    void setHeight(int h) {
        height = h;
    }
protected:
    int width;
    int height;
};

class Rectangle : public Shape {
public:
    int getArea() override {
        return width * height;
    }
//    int getArea() {
//        return width * height;
//    }
};

class Triangle : public Shape {
public:
//    int getArea() override {
//        return (width * height) / 2;
//    }
    int getArea() {
        return (width * height) / 2;
    }
};

//9.4.1.2 抽象类的实例
void test9_4_1_2() {
    Rectangle rectangle;
    Triangle triangle;
    rectangle.setWidth(5);
    rectangle.setHeight(7);
    // 输出对象的面积
    cout << "Total Rectangle area: " << rectangle.getArea() << endl;

    triangle.setWidth(5);
    triangle.setHeight(7);
    // 输出对象的面积
    cout << "Total Triangle area: " << triangle.getArea() << endl;
}

class AbstractClass {
public:
    virtual void pureVirtualFunction() = 0;  // 纯虚函数声明
    //pureVirtualFunction()是一个纯虚函数，它没有提供实现。这样的纯虚函数使得抽象类无法被实例化，而只能作为基类来派生其他类。
};

//派生类必须实现抽象类中的纯虚函数，否则派生类也会成为抽象类。例如：
class ConcreteClass : public AbstractClass {
    //ConcreteClass是一个具体类，它继承自AbstractClass并实现了纯虚函数pureVirtualFunction()。
    //这样，ConcreteClass就可以被实例化，并且必须提供纯虚函数的实现。
public:
    void pureVirtualFunction() override {
        // 纯虚函数的实现
        // ...
        cout << "ConcreteClass pureVirtualFunction" << endl;
    }
};

//9.4.1.3 实现抽象类中的成员函数
void test9_4_1_3() {
    cout << "9.4.1.3 实现抽象类中的成员函数" << endl;
    //通过纯虚函数，C++中的抽象类可以定义接口和规范，并要求派生类实现这些接口。这种机制提供了一种灵活的方式来实现多态和接口继承。
//    AbstractClass aClass;
    ConcreteClass concreteClass;
    concreteClass.pureVirtualFunction();
    ConcreteClass *concreteClass1 = new ConcreteClass();
    concreteClass1->pureVirtualFunction();
}


//9.4.2.1 什么是数据抽象
void test9_4_2_1() {
    //数据抽象是指，只向外界提供关键信息，并隐藏其后台的实现细节，即只表现必要的信息而不呈现细节。
    //数据抽象是一种依赖于接口和实现分离的编程（设计）技术。
    //让我们举一个现实生活中的真实例子，比如一台电视机，您可以打开和关闭、切换频道、调整音量、添加外部组件（如喇叭、录像机、DVD 播放器），但是您不知道它的内部实现细节，也就是说，您并不知道它是如何通过缆线接收信号，如何转换信号，并最终显示在屏幕上。
    //因此，我们可以说电视把它的内部实现和外部接口分离开了，您无需知道它的内部实现原理，直接通过它的外部接口（比如电源按钮、遥控器、声量控制器）就可以操控电视。
    //现在，让我们言归正传，就 C++ 编程而言，C++ 类为数据抽象提供了可能。它们向外界提供了大量用于操作对象数据的公共方法，也就是说，外界实际上并不清楚类的内部实现。
    //例如，您的程序可以调用 sort() 函数，而不需要知道函数中排序数据所用到的算法。
    //实际上，函数排序的底层实现会因库的版本不同而有所差异，只要接口不变，函数调用就可以照常工作。
}

class AbstractClassTag {
private:
    int privateData;  // 私有成员
public:
    AbstractClassTag() {
        //构造中，初始化一堆操作
        privateData = 10;
    }

    int getData() {
        // 公共函数可以访问私有成员
        return privateData;
    }
    // 其他公共函数的声明
};

//9.4.2.2 访问标签强制抽象
void test9_4_2_2() {
    //在 C++ 中，我们使用访问标签来定义类的抽象接口。一个类可以包含零个或多个访问标签：
    //使用公共标签定义的成员都可以访问该程序的所有部分。一个类型的数据抽象视图是由它的公共成员来定义的。
    //使用私有标签定义的成员无法访问到使用类的代码。私有部分对使用类型的代码隐藏了实现细节。

    //数据抽象是一种面向对象编程的概念，用于隐藏类的内部实现细节，只暴露必要的接口给外部使用。通过数据抽象，可以将类的实现细节与接口分离，提高代码的可维护性和安全性。
    //访问标签（Access Specifiers）是C++中用于控制类成员的访问权限的关键字，包括public、private和protected。
    // 其中，public表示公共成员，可以在类的外部访问；private表示私有成员，只能在类的内部访问；protected表示受保护成员，可以在类的内部和派生类中访问。
    //通过将类的实现细节放在私有成员中，可以强制实现数据抽象。外部代码无法直接访问私有成员，只能通过公共接口来访问和操作类的数据。
    cout << "9.4.2.2 访问标签强制抽象" << endl;
    AbstractClassTag abstractClassTag;
    int data = abstractClassTag.getData();
    cout << "公共函数可以访问私有成员 " << data << endl;
}

class Circle {
    //在上述示例中，Circle类表示一个圆，它有一个私有成员radius表示半径。getArea()是一个公共函数，用于计算圆的面积。
    //创建了一个Circle对象c，并通过getArea()函数计算了圆的面积。外部代码无法直接访问和修改Circle类的私有成员radius，只能通过公共接口getArea()来获取圆的面积。
private:
    double radius;
public:
    Circle() {}
    Circle(double r) {
        radius = r;
    }
    double getArea() {
        return 3.14 * radius * radius;
    }
};


//9.4.2.3 数据抽象的实例
void test9_4_2_3() {
    cout << "9.4.2.3 数据抽象的实例" << endl;
    //下面是一个简单的C++数据抽象的实例，展示了如何使用数据抽象来隐藏类的实现细节并提供公共接口：
    Circle c(5.0);
    double area = c.getArea();
    cout << "Area of the circle: " << area  << endl;
    //这个例子展示了数据抽象的好处。外部代码不需要了解Circle类的内部实现细节，只需要通过公共接口来使用类的功能。
    //这样可以隐藏类的实现细节，提高代码的封装性和安全性。
    //通过数据抽象，我们可以将类的实现细节与接口分离，简化了类的使用和理解。
    //同时，如果需要修改Circle类的实现，只需要保持公共接口的稳定性，而不会影响外部代码的使用。这提高了代码的可维护性和扩展性。
}


//9.4.2.5 数据抽象的好处
void test9_4_2_5() {

}



class Adder {
public:
    // 构造函数
    Adder(int i = 0) {
        total = i;
    }
    // 对外的接口
    void addNum(int number) {
        total += number;
    }
    // 对外的接口
    int getTotal() {
        return total;
    };
private:
    // 对外隐藏的数据
    int total;
};

void test1(){
    Adder a;
    a.addNum(10);
    a.addNum(20);
    int num = a.getTotal();
    cout << "total num :" << num << endl;

    Adder *b;
    b->addNum(10);
}
