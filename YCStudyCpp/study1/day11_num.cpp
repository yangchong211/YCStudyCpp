//
// Created by 杨充 on 2023/6/8.
//

#include <iostream>
#include <cmath>

using namespace std;

//C++ 定义数字
void test1();
//C++ 数学运算
void test2();
//C++ 随机数
void test3();

int main() {
    test1();
    test2();
    test3();
    return 0;
}

void test1(){
    short s;
    int i;
    long l;
    float f;
    double d;
    // 数字赋值
    s = 10;
    i = 1000;
    l = 1000000;
    f = 230.47;
    d = 30949.374;
    // 数字输出
    cout << "short  s :" << s << endl;
    cout << "int    i :" << i << endl;
    cout << "long   l :" << l << endl;
    cout << "float  f :" << f << endl;
    cout << "double d :" << d << endl;
    cout << "\n";
    //short  s :10
    //int    i :1000
    //long   l :1000000
    //float  f :230.47
    //double d :30949.4
}

void test2(){
    // 数字定义
    short  s = 10;
    int    i = -1000;
    long   l = 100000;
    float  f = 230.47;
    double d = 200.374;

    // 数学运算
    cout << "sin(d) :" << sin(d) << endl;
    cout << "abs(i)  :" << abs(i) << endl;
    cout << "floor(d) :" << floor(d) << endl;
    cout << "sqrt(f) :" << sqrt(f) << endl;
    cout << "pow( d, 2) :" << pow(d, 2) << endl;
    cout << "\n";
}

//在许多情况下，需要生成随机数。关于随机数生成器，有两个相关的函数。一个是 rand()，该函数只返回一个伪随机数。生成随机数之前必须先调用 srand() 函数。
void test3() {
    int i, j;
    // 设置种子
    srand((unsigned) time(NULL));
    /* 生成 10 个随机数 */
    for (i = 0; i < 10; i++) {
        // 生成实际的随机数
        j = rand();
        cout << "随机数： " << j << endl;
    }
}
