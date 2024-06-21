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

//5.2.1.1 指针的算术运算介绍
void test5_2_1_1();
//5.2.1.2 递增一个指针
void test5_2_1_2();
//5.2.1.3 递减一个指针
void test5_2_1_3();
//5.2.1.4 指针的比较
void test5_2_1_4();

//5.2.2.1 指向指针的指针
void test5_2_2_1();
//5.2.2.2 传递指针给函数
void test5_2_2_2();
//5.2.2.3 从函数返回指针
void test5_2_2_3();

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

    test5_2_1_1();
    test5_2_1_2();
    test5_2_1_3();
    test5_2_1_4();

    test5_2_2_1();
    test5_2_2_2();
    test5_2_2_3();
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


//5.2.1.1 指针的算术运算介绍
void test5_2_1_1() {
    //指针是一个用数值表示的地址。因此，您可以对指针执行算术运算。可以对指针进行四种算术运算：++、--、+、-。
    //指针算术运算的详细解析：
    //加法运算：可以对指针进行加法运算。当一个指针p加上一个整数n时，结果是指针p向前移动n个元素的大小。例如，如果p是一个int类型的指针，每个int占4个字节，那么p + 1将指向p所指向的下一个int元素。
    //减法运算：可以对指针进行减法运算。当一个指针p减去一个整数n时，结果是指针p向后移动n个元素的大小。例如，如果p是一个int类型的指针，每个int占4个字节，那么p - 1将指向p所指向的前一个int元素。
    //指针与指针之间的减法运算：可以计算两个指针之间的距离。当从一个指针p减去另一个指针q时，结果是两个指针之间的元素个数。例如，如果p和q是两个int类型的指针，每个int占4个字节，那么p - q将得到两个指针之间的元素个数。
    //指针与整数之间的比较运算：可以将指针与整数进行比较运算。可以使用关系运算符（如<、>、<=、>=）对指针和整数进行比较。这种比较通常用于判断指针是否指向某个有效的内存位置。
}


//5.2.1.2 递增一个指针
void test5_2_1_2() {
    int var[MAX] = {10,100,200};
    int *ptr;
    ptr = var;
//    ptr = &var;
    cout << "5.2.1.2 递增一个指针" << endl;
    for (int i = 0; i < MAX; i++){
        cout << "Address of var[" << i << "] = ";
        cout << ptr << endl;
        cout << "Value of var[" << i << "] = ";
        cout << *ptr << endl;
        // 移动到下一个位置
        ptr++;
    }
}


//5.2.1.3 递减一个指针
//对指针进行递减运算，即把值减去其数据类型的字节数
void test5_2_1_3() {
    int var[MAX] = {10, 100, 200};
    int *ptr;
    ptr = var;
    for (int i = MAX; i > 0; i--) {
        cout << "Address of var[" << i << "] = ";
        cout << ptr << endl;
        cout << "Value of var[" << i << "] = ";
        cout << *ptr << endl;
        // 移动到下一个位置
        ptr--;
    }
}

//5.2.1.4 指针的比较
void test5_2_1_4() {
    //指针可以用关系运算符进行比较，如 ==、< 和 >。如果 p1 和 p2 指向两个相关的变量，比如同一个数组中的不同元素，则可对 p1 和 p2 进行大小比较。
    //下面的程序修改了上面的实例，只要变量指针所指向的地址小于或等于数组的最后一个元素的地址 &var[MAX - 1]，则把变量指针进行递增：
    int  var[MAX] = {10, 100, 200};
    int  *ptr;

    // 指针中第一个元素的地址
    ptr = var;
    int i = 0;
    while ( ptr <= &var[MAX - 1] ){
        cout << "Address of var[" << i << "] = ";
        cout << ptr << endl;
        cout << "Value of var[" << i << "] = ";
        cout << *ptr << endl;
        // 指向上一个位置
        ptr++;
        i++;
    }
}

//5.2.2.1 指向指针的指针
void test5_2_2_1() {
    //指向指针的指针是一种多级间接寻址的形式，或者说是一个指针链。
    //指针的指针就是将指针的地址存放在另一个指针里面。
    //通常，一个指针包含一个变量的地址。当我们定义一个指向指针的指针时，第一个指针包含了第二个指针的地址，第二个指针指向包含实际值的位置。
    //一个指向指针的指针变量必须如下声明，即在变量名前放置两个星号。例如，下面声明了一个指向 int 类型指针的指针：
    int var;
    int *ptr;
    int **pptr;
    var = 3000;
    ptr = &var;

    pptr = &ptr;  // 使用运算符 & 获取 ptr 的地址
    // 使用 pptr 获取值
    cout << "var 值为 :" << var << endl;
    cout << "*ptr 值为:" << *ptr << endl;
    cout << "**pptr 值为:" << **pptr << endl;
}

// 在写函数时应习惯性的先声明函数，然后在定义函数
void getSeconds(unsigned long *par);
// 函数声明
double getAverage(int *arr, int size);

//5.2.2.2 传递指针给函数
void test5_2_2_2() {
    //C++ 允许您传递指针给函数，只需要简单地声明函数参数为指针类型即可。
    //下面的实例中，我们传递一个无符号的 long 型指针给函数，并在函数内改变这个值：
    unsigned long sec;
    getSeconds(&sec);
    // 输出实际值
    cout << "Number of seconds :" << sec << endl;
    cout << "Number of seconds :" << &sec << endl;

    int balance[5] = {1000,2,3,17,50};
    double avg;
    avg = getAverage(balance,5);
    // 输出返回值
    cout << "Average value is: " << avg << endl;
}

void getSeconds(unsigned long *par){
    // 获取当前的秒数
    *par = time( NULL );
    return;
}

double getAverage(int *arr, int size) {
    int i,sum=0;
    double avg;
    for (int i = 0; i < size; ++i) {
        sum = sum + arr[i];
    }
    avg = double(sum) / size;
    return avg;
}

int *getRandom() {
    static int r[10];
    // 设置种子
    srand((unsigned) time(NULL));
    for (int i = 0; i < 10; ++i) {
        r[i] = rand();
        cout << r[i] << endl;
    }
    return r;
}

//5.2.2.3 从函数返回指针
void test5_2_2_3() {
    //C++ 允许您传递指针给函数，只需要简单地声明函数参数为指针类型即可。
    //下面的实例中，我们传递一个无符号的 long 型指针给函数，并在函数内改变这个值：
    int *p;
    p = getRandom();
    for (int i = 0; i < 10; ++i) {
        cout << "*(p + " << i << ") : ";
        cout << *(p + i) << endl;
    }
}



