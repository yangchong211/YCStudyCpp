//
// Created by 杨充 on 2023/6/8.
//

#include <iostream>

using namespace std;

//整数常量
void test1();
//浮点常量
void test2();
//字符常量
void test3();
void test4();

int main() {
    cout << "Hello\tWorld\n\n";
    test1();
    test2();
    cout << "dou\tbi\n";
    return 0;
}


//整数常量
//整数常量可以是十进制、八进制或十六进制的常量。前缀指定基数：0x 或 0X 表示十六进制，0 表示八进制，不带前缀则默认表示十进制。
//
//整数常量也可以带一个后缀，后缀是 U 和 L 的组合，U 表示无符号整数（unsigned），L 表示长整数（long）。后缀可以是大写，也可以是小写，U 和 L 的顺序任意。
void test1(){
    //212         // 合法的
    //215u        // 合法的
    //0xFeeL      // 合法的
    //078         // 非法的：8 不是八进制的数字
    //032UU       // 非法的：不能重复后缀

    //85         // 十进制
    //0213       // 八进制
    //0x4b       // 十六进制
    //30         // 整数
    //30u        // 无符号整数
    //30l        // 长整数
    //30ul       // 无符号长整数
}

//浮点常量
//浮点常量由整数部分、小数点、小数部分和指数部分组成。您可以使用小数形式或者指数形式来表示浮点常量。
//当使用小数形式表示时，必须包含整数部分、小数部分，或同时包含两者。当使用指数形式表示时， 必须包含小数点、指数，或同时包含两者。带符号的指数是用 e 或 E 引入的。
void test2() {
    //3.14159       // 合法的
    //314159E-5L    // 合法的
    //510E          // 非法的：不完整的指数
    //210f          // 非法的：没有小数或指数
    //.e55          // 非法的：缺少整数或分数
}


//字符串常量
//字符串字面值或常量是括在双引号 "" 中的。一个字符串包含类似于字符常量的字符：普通的字符、转义序列和通用的字符。
//您可以使用 \ 做分隔符，把一个很长的字符串常量进行分行。
void test3(){
    string greeting = "hello, runoob";
    cout << greeting;
    cout << "\n";     // 换行符
    string greeting2 = "hello, runoob";
    cout << greeting2;
}

//定义常量
//在 C++ 中，有两种简单的定义常量的方式：
//使用 #define 预处理器。
//使用 const 关键字。
#define LENGTH 10
#define WIDTH  5
#define NEWLINE '\n'
void test4(){
    int area;
    area = LENGTH * WIDTH;
    cout << area;
    cout << NEWLINE;


    //您可以使用 const 前缀声明指定类型的常量，如下所示：
    const int  LENGTH2 = 10;
    const int  WIDTH2  = 5;
    const char NEWLINE2 = '\n';
    int area2;
    area2 = LENGTH2 * WIDTH2;
    cout << area2;
    cout << NEWLINE2;
}


//定义函数
//函数由一个函数头和一个函数主体组成。下面列出一个函数的所有组成部分：
//返回类型：一个函数可以返回一个值。return_type 是函数返回的值的数据类型。有些函数执行所需的操作而不返回值，在这种情况下，return_type 是关键字 void。
//函数名称：这是函数的实际名称。函数名和参数列表一起构成了函数签名。
//参数：参数就像是占位符。当函数被调用时，您向参数传递一个值，这个值被称为实际参数。参数列表包括函数参数的类型、顺序、数量。参数是可选的，也就是说，函数可能不包含参数。
//函数主体：函数主体包含一组定义函数执行任务的语句。

void test1();
int max1(int ,int);
void test2();
//参数的默认值
int sum(int , int b=20);

int main() {
    test1();
    test2();
    return 0;
}

void test1(){
    // 局部变量声明
    int a = 100;
    int b = 200;
    int ret;
    // 调用函数来获取最大值
    ret = max1(a, b);
    cout << "Max value is : " << ret << endl;
    cout << "\n";
}

void test2(){
    // 局部变量声明
    int a = 100;
    int b = 200;
    int result;
    // 调用函数来添加值
    result = sum(a, b);
    cout << "Total value is :" << result << endl;
    // 再次调用函数
    result = sum(a);
    cout << "Total value is :" << result << endl;
    cout << "\n";
}

// 函数返回两个数中较大的那个数
int max1(int num1, int num2) {
    // 局部变量声明
    int result;
    if (num1 > num2)
        result = num1;
    else
        result = num2;
    return result;
}

int sum(int a , int b){
    int result;
    result = a + b;
    return result;
}