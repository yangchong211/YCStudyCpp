//
// Created by 杨充 on 2023/6/7.
//


#include <iostream>
using namespace std;


//1.3.1.1 bool，布尔类型
void test1_3_1_1();
//1.3.1.2 char，字符型
void test1_3_1_2();
//1.3.1.3 int，整型
void test1_3_1_3();
//1.3.1.4 float，浮点型
void test1_3_1_4();
//1.3.1.5 double，双浮点型
void test1_3_1_5();
//1.3.1.6 void，无类型
void test1_3_1_6();
//1.3.1.7 wchar_t，宽字符型
void test1_3_1_7();


int main() {
    test1_3_1_1();
    test1_3_1_2();
    test1_3_1_3();
    test1_3_1_4();
    test1_3_1_5();
    test1_3_1_6();
    test1_3_1_7();
    return 0;
}

bool isPrime(int number) {
    // 判断一个数是否为素数
    // ...
    return true; // 或者返回false
}


//1.3.1.1 bool，布尔类型
void test1_3_1_1() {
    //bool类型在条件判断、逻辑运算和控制流程中经常被使用。以下是一些bool类型的常见用法：
    //需要注意的是，bool类型在C++中占用一个字节的内存空间，通常被表示为0（false）或1（true）。在条件判断中，除了0表示false外，其他非零值都被视为true。

    //条件判断：在if语句、while循环、for循环等条件判断语句中，可以使用bool类型来判断条件是否满足。
    bool isTrue = true;
    if (isTrue) {
        // 条件为真时执行的代码
    } else {
        // 条件为假时执行的代码
    }

    //逻辑运算：bool类型可以进行逻辑运算，如与（&&）、或（||）、非（!）等。
    bool a = true;
    bool b = false;
    bool result = a && b; // 逻辑与运算，结果为false


    //函数返回值：函数可以返回bool类型的值，用于表示函数执行的成功与否。
    isPrime(10);

    //标志位：bool类型常用于标志位，用于表示某个状态或条件是否满足。
    bool isFinished = false;
    while (!isFinished) {
        // 执行一些操作
//        if (/* 某个条件满足 */) {
//            isFinished = true;
//        }
    }
}

//1.3.1.2 char，字符型
void test1_3_1_2() {
    //在C++中，char是一种字符型数据类型，用于表示单个字符。char类型占用一个字节的内存空间，可以表示256个不同的字符，包括字母、数字、标点符号和特殊字符。
    //char类型可以用于存储字符常量、字符串和进行字符操作。以下是一些char类型的常见用法：

    //存储字符常量：可以使用单引号将字符常量括起来赋值给char变量。
    char ch = 'A';
    //字符串：char类型可以用于表示字符串，但需要使用字符数组或指针来存储多个字符。
    char str[] = "Hello";
    //字符操作：可以使用char类型进行字符操作，如比较、拼接、截取等。
    char ch1 = 'A';
    char ch2 = 'B';
    if (ch1 < ch2) {
        // 执行一些操作
    }

    char str1[] = "Hello";
    char str2[] = "World";
    char result[20];
    strcpy(result, str1); // 复制字符串
    strcat(result, str2); // 拼接字符串

    //ASCII码：char类型使用ASCII码来表示字符。可以使用强制类型转换将整数转换为对应的字符。
    int asciiValue = 65;
    char ch3 = static_cast<char>(asciiValue); // 将整数65转换为字符'A'

    //需要注意的是，char类型既可以表示字符，也可以表示小整数。在进行字符操作时，char类型会被自动转换为对应的ASCII码值。
}

//1.3.1.3 int，整型
void test1_3_1_3() {
    //在C++中，int是一种整型数据类型，用于表示整数。int类型通常占用4个字节的内存空间（32位系统），可以表示范围内的整数值。
    //以下是一些int类型的常见用法：

    //1.声明和初始化：可以使用int关键字声明int类型的变量，并进行初始化。
    int num = 10;

    //2.运算：int类型可以进行常见的数学运算，如加法、减法、乘法和除法。
    int a = 10;
    int b = 5;
    int sum = a + b; // 加法
    int difference = a - b; // 减法
    int product = a * b; // 乘法
    int quotient = a / b; // 除法

    //3.范围：int类型可以表示一定范围内的整数值，通常为-2,147,483,648到2,147,483,647（32位系统）。

    //4.数值溢出：当进行数值计算时，int类型可能会发生溢出。溢出指的是结果超出了int类型所能表示的范围，导致结果不准确。在进行计算时，需要注意溢出的可能性。

    //5.位运算：int类型可以进行位运算，如按位与（&）、按位或（|）、按位异或（^）等。
    int a2 = 5; // 二进制表示为 0101
    int b2 = 3; // 二进制表示为 0011
    int result = a2 & b2; // 按位与运算，结果为 0001，即 1

    //6.数值转换：可以使用强制类型转换将其他类型的值转换为int类型，或将int类型的值转换为其他类型。
    double num2 = 3.14;
    int intValue = static_cast<int>(num2); // 将double类型转换为int类型

    //需要注意的是，int类型的大小和范围可能会因编译器和操作系统的不同而有所变化。可以使用sizeof运算符来获取int类型在当前系统中的字节数。

}


//1.3.1.4 float，浮点型
void test1_3_1_4() {
    //在C++中，float是一种浮点型数据类型，用于表示单精度浮点数。float类型通常占用4个字节的内存空间，可以表示小数或具有较大范围的数值。
    //以下是一些float类型的常见用法：


    //1.声明和初始化：可以使用float关键字声明float类型的变量，并进行初始化。
    float num1 = 3.14;

    //2.运算：float类型可以进行常见的数学运算，如加法、减法、乘法和除法。
    float a = 3.14;
    float b = 2.5;
    float num2 = a + b;

    //3.精度：float类型是单精度浮点数，相对于double类型来说，精度较低。它可以表示大约6到7位有效数字。

    //4.数值范围：float类型可以表示的数值范围较大，通常为±3.4e-38到±3.4e+38。

    //5.数值后缀：在初始化或赋值时，可以使用f或F后缀来指定一个float类型的值。
    float num3 = 3.14f;

    //6.数值转换：可以使用强制类型转换将其他类型的值转换为float类型，或将float类型的值转换为其他类型。
    int intValue = 10;
    float floatValue = static_cast<float>(intValue); // 将int类型转换为float类型


    //需要注意的是，由于浮点数的特性，float类型的精度可能会有一定的误差。在进行浮点数比较时，应该使用适当的误差范围或比较函数。
}


//1.3.1.5 double，双浮点型
void test1_3_1_5() {
    //在C++中，double是一种双精度浮点型数据类型，用于表示双精度浮点数。double类型通常占用8个字节的内存空间，可以表示更大范围和更高精度的浮点数。
    //以下是一些double类型的常见用法：
    //1.声明和初始化：可以使用double关键字声明double类型的变量，并进行初始化。
    double num = 3.14159;
    //2.运算：double类型可以进行常见的数学运算，如加法、减法、乘法和除法。
    double a = 3.14159;
    double b = 2.71828;
    double sum = a + b; // 加法
    double difference = a - b; // 减法
    double product = a * b; // 乘法
    double quotient = a / b; // 除法
    //3.精度：double类型是双精度浮点数，相对于float类型来说，精度更高。它可以表示大约15位有效数字。
    //4.数值范围：double类型可以表示的数值范围较大，通常为±1.7e-308到±1.7e+308。
    //5.数值后缀：在初始化或赋值时，可以使用d或D后缀来指定一个double类型的值。不使用后缀时，默认为double类型。
    double num3 = 3.14159;
    //6.数值转换：可以使用强制类型转换将其他类型的值转换为double类型，或将double类型的值转换为其他类型。
    int intValue = 10;
    double doubleValue = static_cast<double>(intValue); // 将int类型转换为double类型
}


void printMessage() {
    cout << "Hello, World!" << endl;
}

void process(void ) {
    // 执行一些操作
}

//1.3.1.6 void，无类型
void test1_3_1_6() {
    //在C++中，void是一种特殊的数据类型，表示无类型。void类型用于表示不返回任何值的函数、指针或函数参数。
    //以下是一些void类型的常见用法：

    //1.函数返回类型：void用于表示函数不返回任何值。
    printMessage();
    //2.指针类型：void指针可以指向任何类型的数据，但不能直接解引用。通常用于处理未知类型的数据。
    void* ptr;
    int num = 10;
    ptr = &num;
    //3.函数参数：void用于表示函数不接受任何参数。
    process();
    //4.函数指针：void可以用于声明函数指针，指向不返回任何值的函数。
    void (*funcPtr)(); // 声明一个指向不返回任何值的函数的指针
    funcPtr = &printMessage; // 将函数指针指向printMessage函数

    //需要注意的是，void类型不能用于定义变量，因为它没有具体的大小或存储方式。
    //void类型在C++中用于表示没有返回值或不接受参数的情况，它在函数、指针和函数参数中发挥重要作用。
}


//1.3.1.7 wchar_t，宽字符型
void test1_3_1_7() {
    //在C++中，wchar_t是一种宽字符型数据类型，用于表示宽字符。宽字符是一种可以表示更广泛字符集的字符类型，通常用于处理多语言、国际化和Unicode字符。
    //1.声明和初始化：可以使用wchar_t关键字声明wchar_t类型的变量，并进行初始化。
    wchar_t ch = L'A';
    //2.字符串：wchar_t类型可以用于表示宽字符字符串，需要使用宽字符数组或指针来存储多个宽字符。
    wchar_t str[] = L"Hello";
    //3.宽字符操作：可以使用wchar_t类型进行宽字符操作，如比较、拼接、截取等。
    wchar_t ch1 = L'A';
    wchar_t ch2 = L'B';
    if (ch1 < ch2) {
        // 执行一些操作
    }

    wchar_t str1[] = L"Hello";
    wchar_t str2[] = L"World";
    wchar_t result[20];
    wcscpy(result, str1); // 复制宽字符字符串
    wcscat(result, str2); // 拼接宽字符字符串

    //4.宽字符编码：wchar_t类型使用宽字符编码来表示字符，通常是Unicode编码。宽字符编码可以表示更广泛的字符集，包括多语言字符和特殊符号。
    //5.宽字符转换：可以使用宽字符转换函数将wchar_t类型的值转换为其他类型，或将其他类型的值转换为wchar_t类型。
    wchar_t ch3 = L'A';
    int intValue = static_cast<int>(ch3); // 将宽字符转换为整数
    //需要注意的是，wchar_t类型的大小和编码方式可能会因编译器和操作系统的不同而有所变化。在处理宽字符时，需要确保使用正确的编码方式和函数。
}




