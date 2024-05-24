//
// Created by 杨充 on 2023/7/5.
//

#include <iostream>
#include "ostream"
#include "fstream"
using namespace std;

//7.2.1.1 打开方式
void test7_2_1_1();
//7.2.1.2 默认打开模式
void test7_2_1_2();

//7.2.2.1 文件流说明
void test7_2_2_1();
//7.2.2.2 键盘输入写文本文件
void test7_2_2_2();
//7.2.2.3 写入文本内容到文件
void test7_2_2_3();
//7.2.2.4 读取文本文件
void test7_2_2_4();
//7.2.2.5 二进制文件的读/写
void test7_2_2_5();
//7.2.2.7 关闭文件
void test7_2_2_7();


int main() {
//    test7_2_1_1();
    test7_2_1_2();

    test7_2_2_1();
//    test7_2_2_2();
    test7_2_2_3();
    test7_2_2_4();
    test7_2_2_5();
    test7_2_2_7();
    return 0;
}

//7.2.1.1 打开方式
void test7_2_1_1() {
    //在从文件读取信息或者向文件写入信息之前，必须先打开文件。
    //ostream 和 fstream 对象都可以用来打开文件进行写操作，如果只需要打开文件进行读操作，则使用 ifstream 对象。

    char data[100];
    ofstream outfile;
    //打开文件
    //在从文件读取信息或者向文件写入信息之前，必须先打开文件。
    //ofstream 和 fstream 对象都可以用来打开文件进行写操作，如果只需要打开文件进行读操作，则使用 ifstream 对象。
    outfile.open("yc.txt");
    cout << "Writing to the file" << endl;
    cout << "Enter your name: \n";
    cin.getline(data, 100);
}

//7.2.1.2 默认打开模式
void test7_2_1_2() {
    //在从文件读取信息或者向文件写入信息之前，必须先打开文件。
    //ofstream 和 fstream 对象都可以用来打开文件进行写操作，如果只需要打开文件进行读操作，则使用 ifstream 对象。
    //下面是 open() 函数的标准语法，open() 函数是 fstream、ifstream 和 ofstream 对象的一个成员。
    //void open(const char *filename, ios::openmode mode);
    //在这里，open() 成员函数的第一参数指定要打开的文件的名称和位置，第二个参数定义文件被打开的模式。
    //模式标志	描述
    //ios::app	追加模式。所有写入都追加到文件末尾。
    //ios::ate	文件打开后定位到文件末尾。
    //ios::in	打开文件用于读取。
    //ios::out	打开文件用于写入。
    //ios::trunc	如果该文件已经存在，其内容将在打开文件之前被截断，即把文件长度设为 0。

    //您可以把以上两种或两种以上的模式结合使用。例如，如果您想要以写入模式打开文件，并希望截断文件，以防文件已存在，那么您可以使用下面的语法：
    //ofstream outfile;
    //outfile.open("file.dat", ios::out | ios::trunc );
}

//7.2.2.1 文件流说明
void test7_2_2_1() {
    //本教程介绍如何从文件读取流和向文件写入流。这就需要用到 C++ 中另一个标准库 fstream，它定义了三个新的数据类型：
    //数据类型	描述
    //ofstream	该数据类型表示输出文件流，用于创建文件并向文件写入信息。
    //ifstream	该数据类型表示输入文件流，用于从文件读取信息。
    //fstream	该数据类型通常表示文件流，且同时具有 ofstream 和 ifstream 两种功能，这意味着它可以创建文件，向文件写入信息，从文件读取信息。
}

//7.2.2.2 键盘输入写文本文件
void test7_2_2_2() {
    char data[100];
    ofstream outfile;
    //打开文件
    //在从文件读取信息或者向文件写入信息之前，必须先打开文件。
    //ofstream 和 fstream 对象都可以用来打开文件进行写操作，如果只需要打开文件进行读操作，则使用 ifstream 对象。
    outfile.open("yc.txt");
    cout << "Writing to the file" << endl;
    cout << "Enter your name: \n";
    //键盘输入数据
    cin.getline(data, 100);

    // 向文件写入用户输入的数据
    // 使用流插入运算符（ << ）向文件写入信息，就像使用该运算符输出信息到屏幕上一样。
    // 唯一不同的是，在这里您使用的是 ofstream 或 fstream 对象，而不是 cout 对象。
    outfile << data << endl;
    cout << "Enter your age: ";
    cin >> data;
    cin.ignore();

    // 再次向文件写入用户输入的数据
    outfile << data << endl;

    // 关闭打开的文件
    //关闭文件
    //当 C++ 程序终止时，它会自动关闭刷新所有流，释放所有分配的内存，并关闭所有打开的文件。但程序员应该养成一个好习惯，在程序终止前关闭所有打开的文件。
    //下面是 close() 函数的标准语法，close() 函数是 fstream、ifstream 和 ofstream 对象的一个成员。
    outfile.close();
}


//7.2.2.3 写入文本内容到文件
void test7_2_2_3() {
    ofstream file("yc1.txt"); // 创建一个输出文件流对象
    if (file.is_open()) {
        file << "Hello, World!" << std::endl; // 写入文本内容
        file << "This is a sample file." << std::endl;
        file.close(); // 关闭文件
        std::cout << "File written successfully." << std::endl;
    } else {
        std::cout << "Failed to open the file." << std::endl;
    }
}

//7.2.2.4 读取文本文件
void test7_2_2_4() {
    //在 C++ 编程中，我们使用流提取运算符（ >> ）从文件读取信息，就像使用该运算符从键盘输入信息一样。
    //唯一不同的是，在这里您使用的是 ifstream 或 fstream 对象，而不是 cin 对象。

    //首先包含了<iostream>、<fstream>和<string>头文件，分别用于输入输出、文件操作和字符串处理。
    //然后，我们创建了一个ifstream对象file，并指定要读取的文件名为"yc.txt"。
    //接下来，我们使用is_open()方法检查文件是否成功打开。如果文件成功打开，我们使用std::getline()函数逐行读取文件内容，并将每行内容存储在std::string对象line中。
    //然后，我们输出每行内容到标准输出，这里使用std::cout打印到控制台。
    //最后，我们使用close()方法关闭文件，并根据需要输出相应的消息。
    //请注意，在读取文件内容时，我们使用std::getline()函数逐行读取，直到文件结束。每次读取一行内容后，我们输出到标准输出。


    ifstream infile;
    infile.open("yc.txt");
    cout << "Reading from the file" << endl;
    // 使用流提取运算符（ >> ）从文件读取信息，就像使用该运算符从键盘输入信息一样。
    // 唯一不同的是，在这里您使用的是 ifstream 或 fstream 对象，而不是 cin 对象。
    if (infile.is_open()) {
        std::string line;
        char data[100];
        while (std::getline(infile, line)) { // 逐行读取文件内容
            std::cout << "输出每行内容 ：" << line << std::endl; // 输出每行内容
        }
        infile.close();
        std::cout << "File read successfully." << std::endl;
    } else {
        std::cout << "Failed to open the file." << std::endl;
    }
}


//7.2.2.5 二进制文件的读/写
void test7_2_2_5() {
    //首先包含了<iostream>和<fstream>头文件，分别用于输入输出和文件操作。
    //然后，我们创建了一个ofstream对象outFile，并指定要写入的二进制文件名为"yc.bin"，并使用std::ios::binary标志来指示以二进制模式打开文件。
    //接下来，我们使用is_open()方法检查文件是否成功打开。如果文件成功打开，我们可以使用write()方法将数据以二进制形式写入文件。在示例中，我们写入了一个整数数组。
    //最后，我们使用close()方法关闭文件，并根据需要输出相应的消息。
    //接下来，我们创建了一个ifstream对象inFile，并指定要读取的二进制文件名为"yc.bin"，同样使用std::ios::binary标志来指示以二进制模式打开文件。
    //然后，我们使用is_open()方法检查文件是否成功打开。如果文件成功打开，我们可以使用read()方法从文件中读取二进制数据。在示例中，我们读取了一个整数数组。
    //最后，我们输出读取的数据到标准输出。
    //请注意，在读取和写入二进制文件时，我们使用reinterpret_cast来进行类型转换，以便正确地读取和写入二进制数据。

    // 写入二进制文件
    ofstream outFile("yc.bin",ios::out | ios::binary);
    if (outFile.is_open()) {
        int data[5] = {1,2,3,4,5};
        //将数组转化为char
        //outFile.write(data,sizeof(data));
        outFile.write(reinterpret_cast<const char*>(data), sizeof(data));
        std::cout << "Binary file written successfully." << std::endl;
    } else {
        std::cout << "Failed to open the file for writing." << std::endl;
    }

    //读取二进制文件
    ifstream inFile("yc.bin",ios::binary | ios::in);
    if (inFile.is_open()) {
        int data[5];
        inFile.read(reinterpret_cast<char*>(data), sizeof(data));
        for (int i = 0; i < 5; i++) {
            std::cout << data[i] << " ";
        }
        inFile.close();
        std::cout << std::endl << "Binary file read successfully." << std::endl;
    } else {
        std::cout << "Failed to open the file for reading." << std::endl;
    }
}

//7.2.2.7 关闭文件
void test7_2_2_7() {
    //当 C++ 程序终止时，它会自动关闭刷新所有流，释放所有分配的内存，并关闭所有打开的文件。但程序员应该养成一个好习惯，在程序终止前关闭所有打开的文件。
    //下面是 close() 函数的标准语法，close() 函数是 fstream、ifstream 和 ofstream 对象的一个成员。
    //void close();
}


