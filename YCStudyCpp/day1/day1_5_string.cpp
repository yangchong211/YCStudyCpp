//
// Created by 杨充 on 2023/6/6.
// 1.1.2 C++基本语法
//

#include <iostream>

using namespace std;

//1.5.1.1 C 风格字符串
void test1_5_1_1();
//1.5.1.2 函数处理字符串
void test1_5_1_2();
//1.5.1.3 C++中String类
void test1_5_1_3();
//1.5.1.4 输入字符串
void test1_5_1_4();

//1.5.2.1 字符串转化为数组
void test1_5_2_1();
//1.5.2.2 字符串转化为vector向量
void test1_5_2_2();

// main() 是程序开始执行的地方
int main() {
    test1_5_1_1();
    test1_5_1_2();
    test1_5_1_3();
    test1_5_1_4();

    test1_5_2_1();
    test1_5_2_2();
    return 0;
}

//1.5.1.1 C 风格字符串
//C 风格字符串
//C 风格的字符串起源于 C 语言，并在 C++ 中继续得到支持。字符串实际上是使用 null 字符 \0 终止的一维字符数组。因此，一个以 null 结尾的字符串，包含了组成字符串的字符。
void test1_5_1_1() {
    char site1[7] = {'R', 'U', 'N', 'O', 'O', 'B', '\0'};
    char site2[] = "RUNOOB";
    cout << "菜鸟教程: ";
    cout << site1 << "\n" << endl;
    cout << site2 << "\n" << endl;
}

//1.5.1.2 函数处理字符串，C++ 中有大量的函数用来操作以 null 结尾的字符串，strcpy复制，strlen返回长度，strcmp比较是否相同等等
//C++ 中有大量的函数用来操作以 null 结尾的字符串:
void test1_5_1_2(){
    char c[10]; //c其实是个字符串，不是字符。字符串用数组表示，岂不是太复杂了！
    char str1[13] = "runoob";
    char str2[13] = "google";
    char str3[13];
    int len;

    // 复制 str1 到 str3
    strcpy(str3, str1);
    cout << "strcpy( str3, str1) : " << str3 << endl;
    // 连接 str1 和 str2。连接字符串 s2 到字符串 s1 的末尾。
    strcat(str1, str2);
    cout << "strcat( str1, str2): " << str1 << endl;
    // 连接后，str1 的总长度
    len = strlen(str1);
    cout << "strlen(str1) : " << len << endl;
    //如果 s1 和 s2 是相同的，则返回 0；如果 s1<s2 则返回值小于 0；如果 s1>s2 则返回值大于 0。
    int isEqual = strcmp(str1, str2);
    cout << "strcmp(str1) : " << isEqual << endl;
}

//1.5.1.3 C++中String类
//C++ 标准库提供了 string 类类型，支持上述所有的操作，另外还增加了其他更多的功能。我们将学习 C++ 标准库中的这个类，现在让我们先来看看下面这个实例：
void test1_5_1_3() {
    string str1 = "yangchong";
    string str2 = "tangxinyi";
    string str3;
    int len;
    // 复制 str1 到 str3
    str3 = str1;
    cout << "str3 : " << str3 << endl;
    // 连接 str1 和 str2
    str3 = str1 + str2;
    cout << "str1 + str2 : " << str3 << endl;
    // 连接后，str3 的总长度
    len = str3.size();
    cout << "str3.size() :  " << len << endl;
}


//1.5.1.4 输入字符串
//cin.getline() 是在输入一段字符完成后开始读取数据（注意，是输入完成后，以Enter为结束标志）
void test1_5_1_4() {
    int N = 100;
    char X[N];
    cin.getline(X, N);                               //以cin.getline形式输入
    int a = 0, b = 0;
    for (int i = 0; i < N; i++) {
        if (X[i] == '#')                                      //为#为结束标志
            break;
        if (X[i] >= '0' && X[i] <= '9')
            a++;                                         //统计数字个数
        if ((X[i] >= 'a' && X[i] <= 'z') || (X[i] >= 'A' && X[i] <= 'Z'))
            b++;                                      //统计英文字母个数
    }
    cout << "数字个数：" << a << "，字母个数：" << b << endl;
}

//1.5.2.1 字符串转化为数组
void test1_5_2_1() {
    cout << "1.5.2.1 字符串转化为数组" << endl;
    //使用字符数组：可以将字符串的每个字符存储在字符数组中。可以使用std::string的c_str()函数获取字符串的C风格字符数组表示。
    std::string str = "hello";
    const char * cstr = str.c_str();
    int length = str.length();
    char arr[length + 1];
    //转为数组
    std::strcpy(arr,cstr);

    // 打印字符数组
    for (int i = 0; i < length; i++) {
        std::cout << arr[i] << " ";
    }

    cout << "使用std::vector" << endl;
    //使用std::vector：可以使用std::vector来存储字符串的每个字符。
    //可以使用std::string的begin()和end()函数来遍历字符串，并将每个字符添加到std::vector中。
    std::string str2 = "Hello";
    std::vector<char> arr2(str2.begin(), str2.end());
    // 打印字符数组
    for (char ch : arr2) {
        std::cout << ch << " ";
    }

    cout << "使用std::array" << endl;
    //使用std::array：如果字符串的长度是固定的，可以使用std::array来存储字符串的每个字符。
    //可以使用std::string的operator[]运算符来访问字符串的每个字符，并将其存储在std::array中。
    std::string str3 = "Hello";
    std::array<char, 6> arr3;
    for (int i = 0; i < str3.length(); i++) {
        arr3[i] = str3[i];
    }
    // 打印字符数组
    for (char ch : arr3) {
        std::cout << ch << " ";
    }
}


//1.5.2.2 字符串转化为vector向量
void test1_5_2_2() {
    //使用循环遍历字符串并逐个添加到std::vector中：
    std::string str1 = "hello";
    std::vector<char> vec1;
    for (char ch : str1) {
        vec1.push_back(ch);
    }
    // 打印向量中的字符
    for (char ch : vec1) {
        std::cout << ch << " ";
    }


    //使用迭代器将字符串的字符范围复制到std::vector中：
    std::string str2 = "Hello";
    std::vector<char> vec2(str2.begin(), str2.end());
    // 打印向量中的字符
    for (char ch : vec2) {
        std::cout << ch << " ";
    }


    //使用std::copy算法将字符串的字符复制到std::vector中：
    std::string str3 = "Hello";
    std::vector<char> vec3(str3.length());
    std::copy(str3.begin(), str3.end(), vec3.begin());
    // 打印向量中的字符
    for (char ch : vec3) {
        std::cout << ch << " ";
    }

    //这些方法中，选择适合您需求的方法来将字符串转换为std::vector向量。请注意，std::vector会自动调整大小以容纳字符串的字符。
}





