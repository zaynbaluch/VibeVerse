import 'package:client/utils/colors.dart';
import 'package:client/utils/text.dart';
import 'package:flutter/material.dart';

class AnimePage extends StatefulWidget {
  const AnimePage({super.key});

  @override
  State<AnimePage> createState() => _BooksPageState();
}

class _BooksPageState extends State<AnimePage> {
  List<String> readBooks() {
    return [
      'https://cdn.myanimelist.net/images/anime/13/17405.jpg',
      'https://cdn.myanimelist.net/images/anime/6/73245.jpg',
      'https://cdn.myanimelist.net/images/anime/10/47347.jpg',
      'https://cdn.myanimelist.net/images/anime/9/9453.jpg',
      'https://cdn.myanimelist.net/images/anime/1286/99889.jpg',
      'https://cdn.myanimelist.net/images/anime/1223/96541.jpg',
      'https://cdn.myanimelist.net/images/anime/10/78745.jpg',
      'https://cdn.myanimelist.net/images/anime/1171/109222.jpg',
      'https://cdn.myanimelist.net/images/anime/5/64449.jpg',
      'https://cdn.myanimelist.net/images/anime/1806/126216.jpg',
      'https://cdn.myanimelist.net/images/anime/11/39717.jpg',
      'https://cdn.myanimelist.net/images/anime/3/40451.jpg',
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
          RText(text: "From Studio Ghabli", color: AppColor.white, size: 40),
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
