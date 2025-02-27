# eshop-adpro

- [Tutorial Modul 1](#tutorial-modul-1)
- [Tutorial Modul 2](#tutorial-modul-2)
- [Tutorial Modul 3](#tutorial-modul-3)

Link Deployment: https://eshop-adpro-arishashaista.koyeb.app

## Tutorial Modul 1
### Reflection 1
Pada implementasi fitur di kode ini, berikut beberapa prinsip _clean code_ dan praktik _secure coding_ yang telah diterapkan:

1. **Penamaan yang Jelas**

   Nama _class_ dan _method_ mudah dipahami (misalnya ProductController, ProductService, createProduct).


2. **Struktur dan Fungsi Singkat**
   
    Fungsi dan method yang dibuat dipisahkan dalam beberapa kelas (Controller, Service, Repository) sehingga setiap bagian terfokus untuk menangani satu tanggung jawab utama (Single Responsibility Principle).
   Selain itu, setiap _method_ hanya melakukan satu hal, misalnya productListPage() yang hanya mengambil list produk dan mengembalikannya ke view.


3. **_Comment_ yang Tidak Berlebihan**
    
    Kode yang dibuat tidak ada _comment_ yang tidak perlu dan sudah cukup jelas melalui struktur dan penamaan, sehingga tidak ada _commented out_ yang membingungkan.

Adapun hal yang dapat ditingkatkan pada kode yang diimplementasikan:

1. **Validasi Input**
    
    Membuat validasi input seperti nama yang tidak boleh kosong, kuantitas negatif, dan lain-lain. Dapat diatasi dengan menambah _required_ pada kode.

### Reflection 2
1. Setelah membuat unit test, saya merasa lebih yakin bahwa kode yang ditulis telah memenuhi kebutuhan fungsionalnya. Unit test membantu mendeteksi bug yang ada dari awal, sehingga meminimalkan risiko kesalahan saat integrasi atau menjalani aplikasi. Untuk jumlah unit test yang dibutuhkan, tidak ada angka yang pasti, yang terpenting adalah setiap skenario utama, edge cases, dan error handling diuji. Selanjutnya, code coverage adalah parameter yang menunjukkan seberapa banyak baris kode yang dieksekusi ketika test dijalankan. Namun, code coverage yang tinggi tidak menjamin tidak adanya bug pada kode yang dibuat.


2. - **Duplikasi Setup & URL**: Di setiap _test_, terdapat berulang kali kode driver.get("http://localhost:%d/product/..."). Sebaiknya membuat _helper method_ sehingga struktur dan pemeliharaan kodenya menjadi lebih baik.

   - **Nama Variabel & Magic String**: String seperti "Sampo Cap Bambang" dan "abc", terdapat di banyak tempat. Sebaiknya memakai konstanta atau _test fixture_. Jika ingin mengganti string, maka hanya di satu tempat saja dan membuat kode menjadi lebih rapih.
   - **Kurangnya Assertion Spesifik**: Di _test_ baru, misalnya hanya memeriksa apakah jumlah baris di tabel sama dengan angka tertentu. Tanpa _assertion_ tambahan (seperti menegaskan isi data atau urutan), terdapat kemungkinan untuk tidak mendeteksi masalah yang ada, sehingga diperlukan untuk memperluas _assertion_ agar mencerminkan skenario yang diuji.

## Tutorial Modul 2
### Reflection
1. - **Unnecessary Import (ProductController.java)**
     
     `ProductController` menggunakan import wildcard (org.springframework.web.bind.annotation.*), yang tidak direkomendasikan karena bisa mengimpor lebih banyak dari yang dibutuhkan sehingga saya menghapusnya dan mengganti dengan import yang lebih spesifik sesuai dengan kebutuhan. 
   - **UnnecessaryModifier (ProductService.java)**

     Semua method dalam interface secara default sudah public, sehingga penggunaan public pada method di interface tidak diperlukan. Maka, saya menghapus modifier public dari setiap method yang ada di dalam ProductService.


2. Berdasarkan konfigurasi CI/CD yang telah dibuat, sistem ini sepenuhnya memenuhi definisi Continuous Integration (CI) dan Continuous Deployment (CD). Setiap kali ada push atau pull request, workflow `ci.yml` akan menjalankan unit test secara otomatis, `pmd.yml` akan melakukan analisis kode menggunakan PMD untuk memastikan kualitas kode tetap terjaga, dan `scorecard.yml` akan memeriksa keamanan proyek dengan OSSF Scorecard untuk mendeteksi potensi risiko. Dengan mekanisme ini, semua perubahan diuji dan dianalisis sebelum masuk ke _branch_ utama. Selain itu, karena `Koyeb` telah dikonfigurasi untuk _auto-deploy_ saat ada _push_ ke _branch_ tertentu, maka proses Continuous Deployment (CD) juga berjalan secara otomatis. Secara keseluruhan, implementasi CI/CD ini sudah optimal, karena semua perubahan diuji dan diverifikasi sebelum di-_deploy_ secara otomatis.

## Tutorial Modul 3
### Reflection
1. Dalam proyek ini, saya telah menerapkan beberapa prinsip SOLID sebagai berikut:
   - **Single Responsibility Principle (SRP):** Memisahkan `CarController` dari `ProductController` sehingga `CarController` hanya menangani mobil dan `ProductController` hanya menangani produk. Selain itu, `CarServiceImpl` hanya menjalankan logika bisnis untuk mobil dan `CarRepository` hanya berfungsi sebagai layer akses data.
   - **Open/Closed Principle (OCP):** `CarService.java` adalah _interface_, sehingga memungkinkan untuk menambahkan implementasi lain di masa depan tanpa harus mengubah `CarController`.
   - **Liskov Substitution Principle (LSP):** `CarServiceImpl` mengimplementasikan `CarService`, dan `CarController` hanya bergantung pada `CarService`.
   - **Interface Segregation Principle (ISP):** `CarService` hanya memiliki metode yang relevan dengan mobil.
   - **Dependency Inversion Principle (DIP):** `CarController` bergantung pada _abstract_ `CarService`, bukan `CarServiceImpl`.


2. Dengan **SRP**, `CarController` dipisahkan dari `ProductController`, sehingga lebih terorganisir. **OCP** memungkinkan penambahan fitur baru tanpa mengubah `CarController`. **DIP** membuat unit testing lebih mudah dengan menggunakan interface `CarService` dibandingkan jika bergantung langsung pada `CarServiceImpl`. **LSP** memastikan `CarServiceImpl` dapat diganti dengan implementasi lain tanpa mengubah kode yang menggunakannya. **ISP** menjaga _interface_ tetap sederhana dan relevan, memungkinkan penambahan fitur baru tanpa memodifikasi kode lama.


3. Tanpa **SRP**, jika `CarController` masih menyatu dengan `ProductController`, perubahan pada satu fitur dapat berdampak pada fitur lain, menyebabkan kode sulit dipelihara. Jika **OCP** diabaikan, setiap kali ingin mengganti penyimpanan harus mengubah `CarController`, bukan hanya menambahkan implementasi baru. Tanpa **DIP**, `CarController` bergantung langsung pada `CarServiceImpl`, sehingga sulit untuk diuji secara terpisah dan rentan terhadap perubahan internal implementasi. Tanpa **LSP** dapat menyebabkan kode tidak fleksibel, misalnya jika ingin mengganti `CarServiceImpl` dengan `CarServiceMongoImpl`, perlu mengubah seluruh `CarController`. Tanpa **ISP**, _interface_ dapat menjadi terlalu besar dan sulit digunakan ulang.


















