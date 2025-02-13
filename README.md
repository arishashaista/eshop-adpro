# eshop-adpro

## Reflection 1
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

## Reflection 2
1. Setelah membuat unit test, saya merasa lebih yakin bahwa kode yang ditulis telah memenuhi kebutuhan fungsionalnya. Unit test membantu mendeteksi bug yang ada dari awal, sehingga meminimalkan risiko kesalahan saat integrasi atau menjalani aplikasi. Untuk jumlah unit test yang dibutuhkan, tidak ada angka yang pasti, yang terpenting adalah setiap skenario utama, edge cases, dan error handling diuji. Selanjutnya, code coverage adalah parameter yang menunjukkan seberapa banyak baris kode yang dieksekusi ketika test dijalankan. Namun, code coverage yang tinggi tidak menjamin tidak adanya bug pada kode yang dibuat.


2. - **Duplikasi Setup & URL**: Di setiap _test_, terdapat berulang kali kode driver.get("http://localhost:%d/product/..."). Sebaiknya membuat _helper method_ sehingga struktur dan pemeliharaan kodenya menjadi lebih baik.

   - **Nama Variabel & Magic String**: String seperti "Sampo Cap Bambang" dan "abc", terdapat di banyak tempat. Sebaiknya memakai konstanta atau _test fixture_. Jika ingin mengganti string, maka hanya di satu tempat saja dan membuat kode menjadi lebih rapih.
   - **Kurangnya Assertion Spesifik**: Di _test_ baru, misalnya hanya memeriksa apakah jumlah baris di tabel sama dengan angka tertentu. Tanpa _assertion_ tambahan (seperti menegaskan isi data atau urutan), terdapat kemungkinan untuk tidak mendeteksi masalah yang ada, sehingga diperlukan untuk memperluas _assertion_ agar mencerminkan skenario yang diuji.






