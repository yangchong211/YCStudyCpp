//
// Created by 杨充 on 2023/6/14.
//

//C++ 重载运算符和重载函数
//C++ 允许在同一作用域中的某个函数和运算符指定多个定义，分别称为函数重载和运算符重载。

#include <iostream>

using namespace std;


//9.2.1.1 C++ 中的函数重载
void test9_2_1_1();


int main() {
    test9_2_1_1();
    return 0;
}


class printData {
public:
    void print(int i) {
        cout << "整数为: " << i << endl;
    }

    void print(double f) {
        cout << "浮点数为: " << f << endl;
    }

    void print(char c[]) {
        cout << "字符串为: " << c << endl;
    }
};


//9.2.1.1 C++ 中的函数重载
//C++ 中的函数重载
//在同一个作用域内，可以声明几个功能类似的同名函数，但是这些同名函数的形式参数（指参数的个数、类型或者顺序）必须不同。
//您不能仅通过返回类型的不同来重载函数。
void test9_2_1_1() {
    printData pd;
    // 输出整数
    pd.print(5);
    // 输出浮点数
    pd.print(500.263);
    // 输出字符串
    char c[] = "Hello C++";
    pd.print(c);

    //整数为: 5
    //浮点数为: 500.263
    //字符串为: Hello C++
}