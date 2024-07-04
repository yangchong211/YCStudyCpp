//
// Created by 杨充 on 2023/6/8.
//

#include "iostream"

using namespace std;
const int MAX = 3;

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
    test5_2_1_1();
    test5_2_1_2();
    test5_2_1_3();
    test5_2_1_4();

    test5_2_2_1();
    test5_2_2_2();
    test5_2_2_3();
    return 0;
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



