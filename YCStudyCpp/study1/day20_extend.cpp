//
// Created by 杨充 on 2023/6/9.
//

//C++ 继承
//继承代表了 is a 关系。例如，哺乳动物是动物，狗是哺乳动物，因此，狗是动物，等等。

#include <iostream>

using namespace std;

// 基类
class Animal {
    //默认是私有的
    void eat();

    void run() {
        cout << "动物要运动" << endl;
    }

public:
    void sleep();
};

void Animal::eat() {
    cout << "动物吃东西" << endl;
}

void Animal::sleep() {
    cout << "动物要睡觉" << endl;
}


//派生类
class Dog : public Animal {
public:
    void eat() {
        cout << "狗吃屎" << endl;
    }
};

// 基类
class Shape {
public:
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
    int getArea() {
        return (width * height);
    }
};

void test1();
void test2();

int main() {
    Dog dog;
    dog.eat();
    dog.sleep();
    test1();
    test2();
    return 0;
}


void test1() {
    Rectangle rect;
    rect.setWidth(5);
    rect.setHeight(7);
    // 输出对象的面积
    cout << "Total area: " << rect.getArea() << endl;
}

//多继承
//多继承即一个子类可以有多个父类，它继承了多个父类的特性。
// 基类 PaintCost
class PaintCost {
public:
    int getCost(int area) {
        return area * 70;
    }
};

// 派生类。多继承
class Rectangle2 : public Shape, public PaintCost {
public:
    int getArea() {
        return (width * height);
    }
};

void test2() {
    Rectangle2 Rect;
    int area;
    Rect.setWidth(5);
    Rect.setHeight(7);
    area = Rect.getArea();
    // 输出对象的面积
    cout << "Total area: " << Rect.getArea() << endl;
    // 输出总花费
    cout << "Total paint cost: $" << Rect.getCost(area) << endl;
}




