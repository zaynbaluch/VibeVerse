import 'package:client/utils/colors.dart';
import 'package:client/utils/text.dart';
import 'package:flutter/material.dart';

class MoviesPage extends StatefulWidget {
  const MoviesPage({super.key});

  @override
  State<MoviesPage> createState() => _BooksPageState();
}

class _BooksPageState extends State<MoviesPage> {
  List<String> readBooks() {
    return [
      'https://m.media-amazon.com/images/I/81gepf1eMqL._AC_UF1000,1000_QL80_.jpg',
      'https://m.media-amazon.com/images/I/81QckmGleYL._AC_UF1000,1000_QL80_.jpg',
      'https://m.media-amazon.com/images/I/71OZY035QKL._AC_UF1000,1000_QL80_.jpg',
      'https://m.media-amazon.com/images/I/81BE7eeKzAL._AC_UF1000,1000_QL80_.jpg',
      'https://m.media-amazon.com/images/I/81Xy1ugiWeL._AC_UF1000,1000_QL80_.jpg',
      'https://m.media-amazon.com/images/I/81hHy5XrdKL._AC_UF1000,1000_QL80_.jpg',
      'https://m.media-amazon.com/images/I/81YPgi4vpDL._AC_UF1000,1000_QL80_.jpg',
      'https://m.media-amazon.com/images/I/71aFt4+OTOL._AC_UF1000,1000_QL80_.jpg',
      'https://m.media-amazon.com/images/I/81wgcld4wxL._AC_UF894,1000_QL80_.jpg',
      'https://m.media-amazon.com/images/I/71rdsaOMvVL._AC_UF1000,1000_QL80_.jpg',
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
          RText(text: "Comedy", color: AppColor.white, size: 40),
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
        children: List.generate(
          books.length > 8 ? 8 : books.length,
          (index) => Padding(
            padding: const EdgeInsets.symmetric(horizontal: 8.0),
            child: _buildImageCard(books[index]),
          ),
        ),
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
