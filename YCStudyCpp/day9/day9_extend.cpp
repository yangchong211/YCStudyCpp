//
// Created by 杨充 on 2024/6/4.
//
#include <iostream>
using namespace std;

//9.1.1.1 类的继承
void test9_1_1_1();
//9.1.1.2 继承后可访问性
void test9_1_1_2();
//9.1.1.3 构造函数访问顺序
void test9_1_1_3();
//9.1.1.4 子类隐藏父类的成员函数
void test9_1_1_4();
//9.1.3.1 多重继承定义
void test9_1_2_1();
//9.1.3.2 多重继承二义性
void test9_1_2_2();
//9.1.3.3 多重继承的构造顺序
void test9_1_2_3();


int main() {
    test9_1_1_1();
    test9_1_1_2();
    test9_1_1_3();
    test9_1_1_4();
    test9_1_2_1();
    test9_1_2_2();
    test9_1_2_3();
    return 0;
}

class Animal {
public:
    void eat() {
        cout << "Animal 动物要吃饭" << endl;
    };

    void sleep() {
        cout << "Animal 动物要睡觉" << endl;
    };
};

class Dog : public Animal{
public:
    void eat() {
        cout << "Dog 狗要吃饭" << endl;
    };
};

//9.1.1.1 类的继承
void test9_1_1_1() {
    //面向对象程序设计中最重要的一个概念是继承。继承允许我们依据另一个类来定义一个类，这使得创建和维护一个应用程序变得更容易。这样做，也达到了重用代码功能和提高执行效率的效果。
    //当创建一个类时，您不需要重新编写新的数据成员和成员函数，只需指定新建的类继承了一个已有的类的成员即可。这个已有的类称为基类，新建的类称为派生类。
    //继承代表了 is a 关系。例如，哺乳动物是动物，狗是哺乳动物，因此，狗是动物，等等。
    Dog dog;
    dog.eat();
    dog.sleep();
    //Dog 狗要吃饭
    //Animal 动物要睡觉
}

class Base {
public:
    int publicMember;
protected:
    int protectedMember;
private:
    int privateMember;
};

class DerivedPublic : public Base {
    // publicMember 在 DerivedPublic 中仍然是公有的
    // protectedMember 在 DerivedPublic 中变为保护的
    // privateMember 在 DerivedPublic 中不可访问
};

class DerivedProtected : protected Base {
    // publicMember 和 protectedMember 在 DerivedProtected 中都变为保护的
    // privateMember 在 DerivedProtected 中不可访问
};

class DerivedPrivate : private Base {
    // publicMember 和 protectedMember 在 DerivedPrivate 中都变为私有的
    // privateMember 在 DerivedPrivate 中不可访问
};


//9.1.1.2 继承后可访问性
void test9_1_1_2() {
    // 访问控制和继承
    // 访问	   public	protected	private
    // 同一个类	yes	      yes	      yes
    // 派生类	yes	      yes	      no
    // 外部的类	yes	      no	      no
    // 派生类
    //在C++中，继承后的可访问性是指派生类（子类）对基类（父类）成员的访问权限。
    // C++中有三种继承方式：公有继承（public inheritance）、保护继承（protected inheritance）和私有继承（private inheritance），它们会影响派生类对基类成员的访问权限。
    //公有继承（public inheritance）：使用public关键字进行继承。在公有继承中，基类的公有成员在派生类中仍然是公有的，基类的保护成员在派生类中变为保护的，基类的私有成员在派生类中不可访问。
    //保护继承（protected inheritance）：使用protected关键字进行继承。在保护继承中，基类的公有和保护成员在派生类中都变为保护的，基类的私有成员在派生类中不可访问。
    //私有继承（private inheritance）：使用private关键字进行继承。在私有继承中，基类的公有和保护成员在派生类中都变为私有的，基类的私有成员在派生类中不可访问。
//    DerivedPublic derivedPublic;
//    derivedPublic.publicMember;
//
//    DerivedProtected derivedProtected;
//    derivedProtected.publicMember;
//    derivedProtected.protectedMember;
//    derivedProtected.privateMember;
//
//    DerivedPrivate derivedPrivate;
//    derivedPrivate.publicMember;
//    derivedPrivate.protectedMember;
//    derivedPrivate.privateMember;
}


class BaseConstr {
public:
    BaseConstr() {
        cout << "Base constructor" << endl;
    }

    void test1() {
        cout << "Base test1" << endl;
    }
};

class Derived : public BaseConstr {
    //在这个示例中，Derived类继承自Base类。当创建Derived类的对象时，首先会调用Base类的构造函数，然后再调用Derived类的构造函数。
public:
    Derived() {
        cout << "Derived constructor" << endl;
    }
    void test1() {
        cout << "Derived test1" << endl;
    }
};

//9.1.1.3 构造函数访问顺序
void test9_1_1_3() {
    //在C++中，当派生类继承基类时，构造函数的调用顺序是按照以下规则进行的：
    //首先，基类的构造函数会在派生类的构造函数中被调用。这是因为派生类的对象包含了基类的成员，所以在构造派生类对象之前，需要先构造基类对象。
    //构造函数的调用顺序是从最基类开始，逐级向下，直到最终派生类。也就是说，先调用最基类的构造函数，然后是直接派生类的构造函数，再到间接派生类的构造函数。
    //在每个类的构造函数中，成员变量的初始化顺序是按照它们在类中声明的顺序进行的，而不是按照它们在构造函数初始化列表中的顺序。

    Derived d;
    d.test1();
    //Base constructor
    //Derived constructor
}

//9.1.1.4 子类隐藏父类的成员函数
void test9_1_1_4() {
    cout << "9.1.1.4 子类隐藏父类的成员函数" << endl;
    //子类可以通过定义与父类同名的成员函数来隐藏父类的成员函数。这种情况下，当通过子类对象调用该成员函数时，将会调用子类中定义的函数，而不是父类中的函数。
    //这种隐藏父类成员函数的行为发生在以下情况下：
    //子类定义了与父类同名的成员函数，并且它们具有相同的参数列表。
    //子类的成员函数没有使用using关键字来引入父类的同名函数。
    Derived d;
    d.test1();          // 调用 Derived 类中的 display() 函数
    d.BaseConstr::test1();  // 通过作用域解析运算符调用 Base 类中的 display() 函数
    //Base constructor
    //Derived constructor
    //Derived test1
    //Base test1
}

class shape {
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

class PaintCost {
public:
    int getCost(int area) {
        return area * 70;
    }
};

class Rectangle : public shape, public PaintCost {
public:
    int getArea() {
        return width * height;
    }
};

//9.1.2.1 多重继承定义
void test9_1_2_1() {
    //多重继承是指一个派生类可以从多个基类中继承成员。这意味着一个派生类可以拥有多个不同的父类，并继承它们的成员变量和成员函数。
    //在多重继承中，每个基类都需要指定访问权限（public、protected或private），用于控制派生类对基类成员的访问权限。
    //多重继承的一些特点和注意事项：
    //基类的构造函数：当派生类对象被创建时，每个基类的构造函数会按照它们在派生类中的声明顺序被调用。
    //基类成员的访问权限：派生类可以访问每个基类的公有成员，但对于保护成员和私有成员，其访问权限取决于基类在派生类中的访问权限。
    //基类成员的二义性：如果多个基类具有相同的成员函数或成员变量，派生类在使用这些成员时可能会出现二义性。在这种情况下，可以使用作用域解析运算符（::）来指定要访问的基类。
    //菱形继承问题：当多个基类共同继承同一个基类时，可能会出现菱形继承问题。这种情况下，派生类中会存在两个或多个相同的基类子对象，可能导致成员冗余和访问二义性。为了解决这个问题，可以使用虚继承（virtual inheritance）。
    cout << "9.1.2.1 多重继承定义" << endl;
    Rectangle Rect;
    int area;
    Rect.setWidth(5);
    Rect.setHeight(7);
    area = Rect.getArea();
    // 输出对象的面积
    cout << "Total area: " << Rect.getArea() << endl;
    // 输出总花费
    cout << "Total paint cost: $" << Rect.getCost(area) << endl;
    //Total area: 35
    //Total paint cost: $2450
}

class Base1 {
public:
    void display() {
        std::cout << "Base1 display()" << std::endl;
    }
    Base1() {
        std::cout << "Base1 constructor" << std::endl;
    }
};

class Base2 {
public:
    void display() {
        std::cout << "Base2 display()" << std::endl;
    }
    Base2() {
        std::cout << "Base2 constructor" << std::endl;
    }
};

class DerivedYc : public Base1, public Base2 {
public:
    void display() {
        std::cout << "Derived display()" << std::endl;
    }
    DerivedYc() {
        std::cout << "Derived constructor" << std::endl;
    }
};

//9.1.2.2 多重继承二义性
void test9_1_2_2() {
    //在C++的多重继承中，当派生类从多个基类中继承相同的成员函数或成员变量时，可能会导致二义性问题。这种情况下，编译器无法确定应该使用哪个基类的成员，从而导致编译错误。
    //二义性问题主要有两种情况：
    //成员函数二义性：当派生类从多个基类中继承了相同的成员函数时，如果派生类直接调用该成员函数，编译器无法确定应该使用哪个基类的成员函数。这种情况下，可以使用作用域解析运算符（::）来指定要调用的基类成员函数。
    //成员变量二义性：当派生类从多个基类中继承了相同的成员变量时，如果派生类直接访问该成员变量，编译器无法确定应该使用哪个基类的成员变量。这种情况下，可以使用作用域解析运算符（::）来指定要访问的基类成员变量。
    cout << "9.1.2.2 多重继承二义性" << endl;
    DerivedYc d;
    d.display();  // 编译错误，二义性调用
    d.Base1::display();  // 使用作用域解析运算符调用 Base1 类中的 display() 函数
    d.Base2::display();  // 使用作用域解析运算符调用 Base2 类中的 display() 函数

    //在这个示例中，Derived类从Base1和Base2类中继承了相同的display()函数。
    //当通过Derived类的对象调用display()函数时，会导致编译错误，因为编译器无法确定应该使用哪个基类的成员函数。
    //为了解决这个问题，可以使用作用域解析运算符来指定要调用的基类成员函数。

    //需要注意的是，二义性问题在多重继承中是比较常见的，因此在设计和使用多重继承时，应该避免出现二义性，或者通过使用作用域解析运算符来明确指定要使用的基类成员。
}

//9.1.2.3 多重继承的构造顺序
void test9_1_2_3() {
    //在C++中，多重继承的构造函数的调用顺序是按照派生类中基类的声明顺序来确定的。具体来说，构造函数的调用顺序如下：
    //首先，派生类的构造函数会调用直接基类的构造函数。基类的构造函数按照它们在派生类中的声明顺序被调用。
    //如果一个基类又继承了其他基类，那么这些基类的构造函数会在派生类的构造函数中被调用，按照它们在基类中的声明顺序进行调用。
    //最后，派生类自身的构造函数会被调用。
    cout << "9.1.2.3 多重继承的构造顺序" << endl;
    DerivedYc d;
    //Base1 constructor
    //Base2 constructor
    //Derived constructor
}










