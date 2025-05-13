import 'package:client/utils/colors.dart';
import 'package:client/utils/text.dart';
import 'package:flutter/material.dart';

class BooksPage extends StatefulWidget {
  const BooksPage({super.key});

  @override
  State<BooksPage> createState() => _BooksPageState();
}

class _BooksPageState extends State<BooksPage> {
  List<String> readBooks() {
    return [
      //'https://m.media-amazon.com/images/I/81gepf1eMqL._AC_UF1000,1000_QL80_.jpg', // 1984 by George Orwell
      'https://m.media-amazon.com/images/I/71rdsaOMvVL._AC_UF1000,1000_QL80_.jpg', // Dune by Frank Herbert
      'https://m.media-amazon.com/images/I/81YPgi4vpDL._AC_UF1000,1000_QL80_.jpg', // The Alchemist by Paulo Coelho
      'https://m.media-amazon.com/images/I/71aFt4+OTOL._AC_UF1000,1000_QL80_.jpg', // The Midnight Library by Matt Haig
      'https://m.media-amazon.com/images/I/81wgcld4wxL._AC_UF894,1000_QL80_.jpg', // Atomic Habits by James Clear

      'https://m.media-amazon.com/images/I/81QckmGleYL._AC_UF1000,1000_QL80_.jpg', // The Great Gatsby by F. Scott Fitzgerald
      'https://m.media-amazon.com/images/I/71OZY035QKL._AC_UF1000,1000_QL80_.jpg', // Sapiens by Yuval Noah Harari
      'https://m.media-amazon.com/images/I/81BE7eeKzAL._AC_UF1000,1000_QL80_.jpg', // The Hobbit by J.R.R. Tolkien
      // 'https://m.media-amazon.com/images/I/81F+lEUlYWL._AC_UF1000,1000_QL80_.jpg', // Project Hail Mary by Andy Weir
      // 'https://m.media-amazon.com/images/I/91b9nNrLrCL._AC_UF1000,1000_QL80_.jpg', // The Song of Achilles by Madeline Miller
      'https://m.media-amazon.com/images/I/81Xy1ugiWeL._AC_UF1000,1000_QL80_.jpg', // Educated by Tara Westover
      'https://m.media-amazon.com/images/I/81hHy5XrdKL._AC_UF1000,1000_QL80_.jpg', // The Four Winds by Kristin Hannah
      'https://m.media-amazon.com/images/I/71xMttNhr6L._AC_UF1000,1000_QL80_.jpg', // Where the Crawdads Sing by Delia Owens
      'https://m.media-amazon.com/images/I/71jLBXtWJWL._AC_UF1000,1000_QL80_.jpg', // The Silent Patient by Alex Michaelides
    ];
  }

  int maxBooksToShow = 8;
  @override
  Widget build(BuildContext context) {
    return Container(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.start,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          RText(text: "Recommended for you", color: AppColor.white, size: 40),
          Container(child: _buildReadBooksRow()),
          SizedBox(height: 30),
          RText(text: "Non-Fiction", color: AppColor.white, size: 40),
          Container(child: _buildReadBooksRow()),
        ],
      ),
    );
  }

  Widget _buildReadBooksRow() {
    List<String> books = readBooks();

    return SingleChildScrollView(
      scrollDirection: Axis.horizontal,
      child: Row(
        children: List.generate(books.length > 8 ? 8 : books.length, (index) {
          // Check if the index is 2, then navigate to the '/book' route
          return Padding(
            padding: const EdgeInsets.symmetric(horizontal: 8.0),
            child: GestureDetector(
              onTap: () {
                if (index == 5) {
                  // Navigate to the '/book' route when index 2 is clicked
                  Navigator.pushNamed(context, '/book');
                }
              },
              child: _buildImageCard(books[index]),
            ),
          );
        }),
      ),
    );
  }
}

Widget _buildImageCard(String imageUrl) {
  return Padding(
    padding: const EdgeInsets.only(right: 10),
    child: ClipRRect(
      borderRadius: BorderRadius.circular(12),
      child: Image.network(
        imageUrl,
        width: 140,
        height: 200,
        fit: BoxFit.cover,
        loadingBuilder: (context, child, loadingProgress) {
          if (loadingProgress == null) return child;
          return SizedBox(
            width: 90,
            height: 120,
            child: Center(child: CircularProgressIndicator()),
          );
        },
        errorBuilder: (context, error, stackTrace) {
          return Container(
            width: 70,
            height: 100,
            color: Colors.grey,
            child: Icon(Icons.broken_image, color: Colors.white),
          );
        },
      ),
    ),
  );
}
