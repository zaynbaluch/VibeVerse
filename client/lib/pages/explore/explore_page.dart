import 'package:client/main_scaffold.dart';
import 'package:client/pages/explore/anime_section.dart';
import 'package:client/pages/explore/books_section.dart';
import 'package:client/pages/explore/games_section.dart';
import 'package:client/pages/explore/movies_section.dart';
import 'package:client/utils/colors.dart';
import 'package:client/utils/headings.dart';
import 'package:client/utils/text.dart';
import 'package:flutter/material.dart';

class ExplorePage extends StatefulWidget {
  const ExplorePage({super.key});

  @override
  State<ExplorePage> createState() => _ExplorePageState();
}

class _ExplorePageState extends State<ExplorePage> {
  String activeCategory = 'Books';
  String searchQuery = '';

  final List<String> categories = ['Books', 'Movies', 'Anime', 'Games'];

  Widget _getActivePage() {
    switch (activeCategory) {
      case 'Books':
        return BooksPage();
      case 'Movies':
        return MoviesPage();
      case 'Anime':
        return AnimePage();
      case 'Games':
        return GamesPage();
      default:
        return Container();
    }
  }

  @override
  Widget build(BuildContext context) {
    return MainScaffold(
      currentRoute: 'explore',
      onNavigate: (route) {
        Navigator.pushReplacementNamed(context, '/$route');
      },
      body: Container(
        padding: const EdgeInsets.all(16),
        constraints: BoxConstraints(
          minHeight: MediaQuery.of(context).size.height,
        ),
        child: SingleChildScrollView(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              RText(text: "Explore", color: AppColor.white, size: 30),

              const SizedBox(height: 16),

              // Search Bar
              TextField(
                onChanged: (value) {
                  setState(() {
                    searchQuery = value;
                  });
                },
                decoration: InputDecoration(
                  hintText: 'Search...',
                  prefixIcon: const Icon(Icons.search),
                  filled: true,
                  fillColor: Colors.white,
                  contentPadding: const EdgeInsets.symmetric(vertical: 10.0),
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(30.0),
                    borderSide: BorderSide.none,
                  ),
                ),
              ),

              const SizedBox(height: 16),

              // Category Buttons
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceAround,
                children:
                    categories.map((category) {
                      final isActive = activeCategory == category;
                      return Expanded(
                        child: TextButton(
                          onPressed: () {
                            setState(() {
                              activeCategory = category;
                            });
                          },
                          style: TextButton.styleFrom(
                            backgroundColor:
                                isActive ? AppColor.purple : Colors.transparent,
                            foregroundColor: AppColor.white,
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(10),
                            ),
                          ),
                          child: RText(text: category, color: AppColor.white),
                        ),
                      );
                    }).toList(),
              ),

              const SizedBox(height: 16),

              // Active Page
              _getActivePage(),
            ],
          ),
        ),
      ),
    );
  }
}
