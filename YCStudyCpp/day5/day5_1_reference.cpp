//
// Created by 杨充 on 2023/6/8.
//

#include "iostream"

using namespace std;
const int MAX = 3;

//5.1.1.1 什么是指针
void test5_1_1_1();
//5.1.1.2 指针运算符和取地址运算符
void test5_1_1_2();
//5.1.1.3 变量与指针
void test5_1_1_3();
//5.1.1.4 指针使用
void test5_1_1_4();
//5.1.1.5 指针类型强制转换
void test5_1_1_5();
//5.1.1.6 Null 指针
void test5_1_1_6();
//5.1.1.7 指针 vs 数组
void test5_1_1_7();

//5.1.2.2 C++ 中创建引用
void test5_1_2_1();
//5.1.2.3 把引用作为参数
void test5_1_2_3();
//5.1.2.4 把引用作为返回值
void test5_1_2_4();


int main() {
    test5_1_1_1();
    test5_1_1_2();
    test5_1_1_3();
    test5_1_1_4();
    test5_1_1_5();
    test5_1_1_6();
    test5_1_1_7();

    test5_1_2_1();
    test5_1_2_3();
    test5_1_2_4();
    return 0;
}

//5.1.1.1 什么是指针
void test5_1_1_1() {
    //通过指针，可以简化一些 C++ 编程任务的执行，还有一些任务，如动态内存分配，没有指针是无法执行的。
    //每一个变量都有一个内存位置，每一个内存位置都定义了可使用连字号（&）运算符访问的地址，它表示了在内存中的一个地址。
}

//5.1.1.2 指针运算符和取地址运算符
void test5_1_1_2() {
    int var1;
    char var2[10];
    cout << "var1 变量的地址： ";
    cout << &var1 << endl;
    cout << "var2 变量的地址： ";
    cout << &var2 << endl;

    //每一个变量都有一个内存位置，每一个内存位置都定义了可使用连字号（&）运算符访问的地址，它表示了在内存中的一个地址。
    int var3;
    char var4[20];
    //& 符号，表示访问内存地址
    cout << "var3变量的地址" << &var3 << endl;
    cout << "var4变量的地址" << &var4 << endl;
}


//5.1.1.3 变量与指针
//指针是一个变量，其值为另一个变量的地址，即，内存位置的直接地址。
//就像其他变量或常量一样，您必须在使用指针存储其他变量地址之前，对其进行声明。指针变量声明的一般形式为：
void test5_1_1_3() {
    //内存直接地址
    int    *ip;    /* 一个整型的指针 */
    double *dp;    /* 一个 double 型的指针 */
    float  *fp;    /* 一个浮点型的指针 */
    char   *ch;    /* 一个字符型的指针 */
}

//5.1.1.4 指针使用
void test5_1_1_4() {
    int var = 20;   // 实际变量的声明
    int *ip;        // 指针变量的声明
    ip = &var;       // 在指针变量中存储 var 的地址
//    int *ip = &var;   //等同于上面的两行代码
    cout << "实际变量" << var << endl;
    cout << "变量中存储地址" << &var << endl;

    //指针：
    // 输出在指针变量中存储的地址。
    cout << "在指针变量中存储的地址" << ip << endl;
    // 访问指针中地址的值
    cout << "指针中具体的值，即读取地址后获得的值" << *ip << endl;
    cout << "指针变量中存储的地址" << &ip << endl;


    //& 符号的意思是取地址，也就是返回一个对象在内存中的地址。
    //* 符号的意思是取得一个指针所指向的对象。 也就是如果一个指针保存着一个内存地址，那么它就返回在那个地址的对象。
    //简单点就是：&：取址。* ：取值。


    //实际变量20
    //变量中存储地址0x16f1b358c
    //指针变量中存储的地址0x16f1b358c
    //指针中地址的值20
    //指针变量中存储的地址0x16f1b3580
}

//5.1.1.5 指针类型强制转换
void test5_1_1_5() {
    //指针的值以及指针指向地址的值对应为数据的地址和该地址内存储数据的值，故根据CPU的大小端类型，将指针转换类型后继续操作应注意大小端。
    char str[4] = {0x12,0x34,0x56,0x78};
    char *ptr = str;
    cout << "value of *ptr " << hex << (int)(*ptr) << endl;//hex输出必须对应int类型，否则输出ASCII码
    cout << "value of *ptr " << hex << (int)(*(int*)ptr) << endl;//hex输出必须对应int类型，否则输出ASCII码
}

//5.1.1.6 Null 指针
void test5_1_1_6() {
    //在变量声明的时候，如果没有确切的地址可以赋值，为指针变量赋一个 NULL 值是一个良好的编程习惯。赋为 NULL 值的指针被称为空指针。
    //NULL 指针是一个定义在标准库中的值为零的常量。
    int *p = NULL;
    cout << "ptr 的值是 " << p << endl ;

    //在大多数的操作系统上，程序不允许访问地址为 0 的内存，因为该内存是操作系统保留的。
    //然而，内存地址 0 有特别重要的意义，它表明该指针不指向一个可访问的内存位置。但按照惯例，如果指针包含空值（零值），则假定它不指向任何东西。
    //因此，如果所有未使用的指针都被赋予空值，同时避免使用空指针，就可以防止误用一个未初始化的指针。很多时候，未初始化的变量存有一些垃圾值，导致程序难以调试。
}

//5.1.1.7 指针 vs 数组
void test5_1_1_7() {
    //指针和数组是密切相关的。事实上，指针和数组在很多情况下是可以互换的。
    //例如，一个指向数组开头的指针，可以通过使用指针的算术运算或数组索引来访问数组。请看下面的程序：
    int  var[MAX] = {10, 100, 200};
    int  *ptr;
    ptr = var;    // 指针中的数组地址

    //如果是基本数据，则指针指向常量的地址值
    int i;
    int *ptri;
    ptri = &i;
    cout << "5.1.1.7 指针 vs 数组" << endl;
    for (int i = 0; i < MAX; i++){
        cout << "var[" << i << "]的内存地址为 ";
        cout << ptr << endl;
        cout << "var[" << i << "] 的值为 ";
        cout << *ptr << endl;
        // 移动到下一个位置
        ptr++;
    }
}

//5.1.2.2 C++ 中创建引用
void test5_1_2_1() {
    //在这些声明中，& 读作引用。因此，第一个声明可以读作 "r 是一个初始化为 i 的整型引用"，第二个声明可以读作 "s 是一个初始化为 d 的 double 型引用"。
    //下面的实例使用了 int 和 double 引用：

    //声明简单的变量
    int i;
    double d;

    //声明引用变量
    int &r = i;
    double &s = d;

    int    x = i;
    double y = d;

    i = 5;
    cout << "Value of i : " << i << endl;
    cout << "Value of i reference : " << r  << endl;
    cout << "Value of i : " << x  << endl;

    d = 11.7;
    cout << "Value of d : " << d << endl;
    cout << "Value of d reference : " << s  << endl;
    cout << "Value of d : " << y << endl;
}

// 函数声明
void swap(int& x, int& y);

//5.1.2.3 把引用作为参数
void test5_1_2_3() {
    int a = 100;
    int b = 200;
    cout << "交换前，a 的值：" << a << endl;
    cout << "交换前，b 的值：" << b << endl;

    /* 调用函数来交换值 */
    swap(a, b);

    cout << "交换后，a 的值：" << a << endl;
    cout << "交换后，b 的值：" << b << endl;
}

void swap(int &x , int &y) {
    int temp;
    temp = x;
    x = y;
    y = temp;
}

double vals[] = {10.1, 12.6, 33.1, 24.1, 50.0};
double &setValues(int i) {
    double &ref = vals[i];
    return ref;
}


//5.1.2.4 把引用作为返回值
void test5_1_2_4() {
    //通过使用引用来替代指针，会使 C++ 程序更容易阅读和维护。C++ 函数可以返回一个引用，方式与返回一个指针类似。
    //当函数返回一个引用时，则返回一个指向返回值的隐式指针。这样，函数就可以放在赋值语句的左边。例如，请看下面这个简单的程序：
    cout << "改变前的值" << endl;
    for (int i = 0; i < 5; i++) {
        cout << "vals[" << i << "] = ";
        cout << vals[i] << endl;
    }
    setValues(1) = 20.23; // 改变第 2 个元素
    setValues(3) = 70.8;  // 改变第 4 个元素
    cout << "改变后的值" << endl;
    for (int i = 0; i < 5; i++) {
        cout << "vals[" << i << "] = ";
        cout << vals[i] << endl;
    }
}
