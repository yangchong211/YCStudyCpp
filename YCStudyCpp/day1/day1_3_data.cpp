//
// Created by 杨充 on 2023/6/7.
//


#include <iostream>
using namespace std;
//之前一些编译器使用 C++11 的编译参数是 -std=c++11
//g++ -std=c++11 day1_3_data.cpp


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

//1.3.2.1 signed，有符号类型是一种整数类型
void test1_3_2_1();
//1.3.2.2 unsigned，无符号类型是一种整数类型
void test1_3_2_2();
//1.3.2.3 short，short是一种有符号短整数类型
void test1_3_2_3();
//1.3.2.4 long，有符号长整数类型，通常为32位或64位，范围取决于编译器和平台
void test1_3_2_4();

//1.3.3.1 size_t，是一种无符号整数类型，用于表示对象的大小或数组的索引
void test1_3_3_1();
//1.3.3.2 uint8_t，uint8_t是一种固定宽度的无符号整数类型
void test1_3_3_2();
//1.3.3.3 enum，枚举类型
void test1_3_3_3();
//1.3.3.4 auto，自动推导变量的类型
void test1_3_3_4();

//1.3.4.1 typedef声明
void test1_3_4_1();


int main() {
//    test1_3_1_1();
//    test1_3_1_2();
//    test1_3_1_3();
//    test1_3_1_4();
//    test1_3_1_5();
//    test1_3_1_6();
//    test1_3_1_7();

    test1_3_2_1();
    test1_3_2_2();
    test1_3_2_3();
    test1_3_2_4();

    test1_3_3_1();
    test1_3_3_2();
    test1_3_3_3();
    test1_3_3_4();

    test1_3_4_1();
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


//1.3.2.1 signed，有符号类型是一种整数类型
void test1_3_2_1() {
    //在C++中，signed是一个关键字，用于声明有符号类型的变量。
    //C++中的整数类型可以分为有符号类型和无符号类型。有符号类型可以表示正数、负数和零，而无符号类型只能表示非负数和零。
    //当您声明一个整数类型的变量时，如果不显式指定类型为有符号或无符号，默认情况下，int、short、long等整数类型都是有符号的。

    signed int x;      // 声明一个有符号整数变量
    signed short y;    // 声明一个有符号短整数变量
    signed long z;     // 声明一个有符号长整数变量

    //需要注意的是，signed关键字在大多数情况下是可选的，因为默认情况下整数类型就是有符号的。
    //但是，使用signed关键字可以增加代码的可读性和明确性，特别是在与无符号类型进行混合运算时。
}


//1.3.2.2 unsigned，无符号类型是一种整数类型
void test1_3_2_2() {
    cout << "1.3.2.2 unsigned，无符号类型是一种整数类型" << endl;
    //当我们需要处理非负数和零的情况时，可以使用C++中的无符号类型。下面是一些使用无符号类型的案例示例：
    std::vector<int> numbers = {1, 2, 3, 4, 5};
    for (unsigned int i = 0; i < numbers.size(); ++i) {
        std::cout << numbers[i] << " ";
    }
    //在上述示例中，我们使用无符号整数类型unsigned int来迭代遍历numbers向量中的元素。由于向量的大小是非负数，因此使用无符号类型更合适。
}


//1.3.2.3 short，short是一种有符号短整数类型
void test1_3_2_3() {
    cout << "1.3.2.3 short，short是一种有符号短整数类型" << endl;
    short a = 10;
    int b = 5;
    int sum = a + b;
    std::cout << "Sum: " << sum << std::endl;
    //在上述示例中，我们将一个short类型的变量a和一个int类型的变量b相加。由于short类型会自动提升为int类型进行运算，所以结果也是int类型。
    //这些示例展示了使用short类型的一些常见情况。short类型通常用于存储较小的有符号整数值，可以节省内存空间。
}


//1.3.2.4 long，有符号长整数类型，通常为32位或64位，范围取决于编译器和平台
void test1_3_2_4() {
    cout << "1.3.2.4 long，有符号长整数类型" << endl;
    long a = 1000000;
    int b = 500000;
    long sum = a + b;
    std::cout << "Sum: " << sum << std::endl;
    //在上述示例中，我们将一个long类型的变量a和一个int类型的变量b相加。由于int类型会自动提升为long类型进行运算，所以结果也是long类型。
    //这些示例展示了使用long类型的一些常见情况。long类型通常用于存储较大的有符号整数值。
}


//1.3.3.1 size_t，是一种无符号整数类型，用于表示对象的大小或数组的索引
void test1_3_3_1() {
    cout << "1.3.3.1 size_t，是一种无符号整数类型" << endl;
    //size_t 它是一种 整型 类型，里面保存的是一个整数，就像 int, long 那样。这种整数用来记录一个大小(size)。
    //size_t 的全称应该是 size type，就是说 一种用来记录大小的数据类型。
    //通常我们用 sizeof(XXX) 操作，这个操作所得到的结果就是 size_t 类型。
    //因为 size_t 类型的数据其实是保存了一个整数，所以它也可以做加减乘除，也可以转化为 int 并赋值给 int 类型的变量。
    int i = 10;
    int j = 11;
    size_t size1 = sizeof(i);
    size_t size2 = sizeof(j);
    std::cout << "size1: " << size1 << std::endl;
    std::cout << "size2: " << size2 << std::endl;
    //size_t类型通常用于与sizeof运算符一起使用，以获取对象或类型的大小。它也常用于表示数组的长度或循环的计数器。
    size_t size = 10;  // 使用size_t类型声明变量并赋值为10
    std::cout << "Size: " << size << std::endl;
}


//1.3.3.2 uint8_t，uint8_t是一种固定宽度的无符号整数类型
void test1_3_3_2() {
    cout << "1.3.3.2 uint8_t，uint8_t是一种固定宽度的无符号整数类型" << endl;
    //数组操作：
    uint8_t arr[] = {10, 20, 30, 40, 50};
    for (int i = 0; i < sizeof(arr) / sizeof(arr[0]); ++i) {
        std::cout << static_cast<int>(arr[i]) << " ";
    }
    //在上述示例中，我们使用uint8_t类型的数组arr存储一些无符号8位整数。
    //然后，我们使用循环遍历数组并打印每个元素。由于uint8_t是一个无符号类型，我们使用static_cast<int>将其转换为int类型以便打印。


    //位操作：
    uint8_t value = 0b10101010;
    uint8_t mask = 0b00001111;
    uint8_t result = value & mask;
    std::cout << static_cast<int>(result) << std::endl;
    //在上述示例中，我们使用uint8_t类型的变量value存储一个8位的二进制值。
    //然后，我们定义一个掩码mask，并使用按位与操作符&将value与mask进行位操作。最后，我们打印结果。


    //数值范围：
    std::cout << "Minimum value: " << static_cast<int>(std::numeric_limits<uint8_t>::min()) << std::endl;
    std::cout << "Maximum value: " << static_cast<int>(std::numeric_limits<uint8_t>::max()) << std::endl;
    //在上述示例中，我们使用std::numeric_limits<uint8_t>::min()和std::numeric_limits<uint8_t>::max()函数来获取uint8_t类型的最小值和最大值，并将其打印到控制台。

    //这些示例展示了使用uint8_t类型的一些常见情况。uint8_t类型通常用于需要确切8位宽度的无符号整数值的场景。


}

//1.3.3.3 enum，枚举类型
void test1_3_3_3() {
    cout << "1.3.3.3 enum，枚举类型" << endl;
    //枚举类型
    //枚举类型(enumeration)是C++中的一种派生数据类型，它是由用户定义的若干枚举常量的集合。
    //如果枚举没有初始化, 即省掉"=整型常数"时, 则从第一个标识符开始。
    //例如，下面的代码定义了一个颜色枚举，变量 c 的类型为 color。最后，c 被赋值为 "blue"。
    enum color {
        red, green, blue
    } c;
    enum day {
        day1, day2, day3, day4
    } d;
    enum yc {
        tangguo, xinyi, yc
    };
    c = blue;
    d = day1;
    cout << "color枚举" << c << endl;
    cout << "day枚举" << d << endl;
    //默认情况下，第一个名称的值为 0，第二个名称的值为 1，第三个名称的值为 2，以此类推。但是，您也可以给名称赋予一个特殊的值，只需要添加一个初始值即可。
    //例如，在下面的枚举中，green 的值为 5。blue 的值为 6，因为默认情况下，每个名称都会比它前面一个名称大 1，但 red 的值依然为 0。
    enum color2 {
        red2, green2 = 5, blue2
    };
    cout << "color2枚举" << red2 << " , " << green2 << " , " << blue2 << endl;
    //color枚举2
    //day枚举0
    //color2枚举0 , 5 , 6
}

//1.3.3.4 auto，自动推导变量的类型
void test1_3_3_4() {
    //在C++中，auto是一个关键字，用于自动推导变量的类型。使用auto关键字可以让编译器根据变量的初始化表达式自动推断出变量的类型，从而简化代码并提高可读性。
    //
    //下面是auto关键字的基本用法：
    auto x = 10;  // 推断为int类型
    auto y = 3.14;  // 推断为double类型
    auto z = "Hello";  // 推断为const char*类型
    //在上述示例中，我们使用auto关键字声明了一个变量variable，并将其初始化为value。编译器会根据value的类型自动推断出variable的类型。

    //auto关键字的使用有以下几个注意点：
    //自动类型推导：auto关键字会根据初始化表达式的类型自动推导变量的类型。这意味着变量的类型将根据初始化值的类型而变化。
    //初始化必须存在：使用auto关键字声明变量时，必须进行初始化。编译器需要初始化表达式来推断变量的类型。
    //类型推导规则：auto关键字使用的类型推导规则与模板类型推导规则类似。它会考虑初始化表达式的值和引用性质，从而确定变量的类型。
    //可读性和简洁性：使用auto关键字可以使代码更加简洁和可读，特别是在处理复杂的类型或使用迭代器等情况下。
    //需要注意的是，auto关键字并不是万能的，它并不适用于所有情况。在某些情况下，显式指定变量的类型可能更加清晰和明确。
}

//1.3.4.1 typedef声明
void test1_3_4_1() {
    //您可以使用 typedef 为一个已有的类型取一个新的名字。下面是使用 typedef 定义一个新类型的语法：
    //例如，下面的语句会告诉编译器，feet 是 int 的另一个名称：
    typedef int feet;
    //现在，下面的声明是完全合法的，它创建了一个整型变量 distance：
    feet distance;
    typedef int yc;
    yc d;
    d = 100;

    //typedef 可以声明各种类型名，但不能用来定义变量。用 typedef 可以声明数组类型、字符串类型，使用比较方便。
    //用typedef只是对已经存在的类型增加一个类型名，而没有创造新的类型。
}










