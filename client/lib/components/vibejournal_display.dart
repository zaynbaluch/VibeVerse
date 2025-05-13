import 'package:client/utils/colors.dart';
import 'package:client/utils/headings.dart';
import 'package:client/utils/text.dart';
import 'package:flutter/material.dart';

class VibejournalDisplay extends StatefulWidget {
  const VibejournalDisplay({super.key});

  @override
  State<VibejournalDisplay> createState() => _VibeboardDisplayState();
}

class _VibeboardDisplayState extends State<VibejournalDisplay> {
  List<String> _getCurrentlyReadingBooks() {
    return [
      'https://m.media-amazon.com/images/I/81YPgi4vpDL._AC_UF1000,1000_QL80_.jpg',
      'https://m.media-amazon.com/images/I/71aFt4+OTOL._AC_UF1000,1000_QL80_.jpg',
      'https://m.media-amazon.com/images/I/81wgcld4wxL._AC_UF894,1000_QL80_.jpg',
      'https://m.media-amazon.com/images/I/71rdsaOMvVL._AC_UF1000,1000_QL80_.jpg',
    ];
  }

  List<String> _getWantToReadBooks() {
    return [
      'https://m.media-amazon.com/images/I/81F+lEUlYWL._AC_UF1000,1000_QL80_.jpg',
      'https://m.media-amazon.com/images/I/91b9nNrLrCL._AC_UF1000,1000_QL80_.jpg',
      'https://m.media-amazon.com/images/I/81Xy1ugiWeL._AC_UF1000,1000_QL80_.jpg',
      'https://m.media-amazon.com/images/I/81hHy5XrdKL._AC_UF1000,1000_QL80_.jpg',
      'https://m.media-amazon.com/images/I/71xMttNhr6L._AC_UF1000,1000_QL80_.jpg',
    ];
  }

  List<String> _getReadBooks() {
    return [
      'https://m.media-amazon.com/images/I/71KI-FxH47L._AC_UF1000,1000_QL80_.jpg',
      'https://m.media-amazon.com/images/I/81gepf1eMqL._AC_UF1000,1000_QL80_.jpg',
      'https://m.media-amazon.com/images/I/81QckmGleYL._AC_UF1000,1000_QL80_.jpg',
      'https://m.media-amazon.com/images/I/71OZY035QKL._AC_UF1000,1000_QL80_.jpg',
      'https://m.media-amazon.com/images/I/81BE7eeKzAL._AC_UF1000,1000_QL80_.jpg',
      'https://m.media-amazon.com/images/I/71kxa1-0mfL._AC_UF1000,1000_QL80_.jpg',
    ];
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Container(
          width: double.infinity,

          color: Color.fromARGB(255, 75, 75, 75),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Padding(
                padding: const EdgeInsets.only(top: 10.0, left: 30, right: 10),
                child: RText(text: "My Books", size: 25, color: AppColor.green),
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Container(
                    padding: EdgeInsets.all(10),

                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Container(
                          padding: EdgeInsets.only(left: 10),

                          width: 350,
                          color: Colors.transparent,
                          child: SingleChildScrollView(
                            scrollDirection: Axis.horizontal,
                            child: Row(
                              children: [_buildImageCard(_getReadBooks()[3])],
                            ),
                          ),
                        ),
                        RText(text: "Currently Reading", color: AppColor.white),
                      ],
                    ),
                  ),
                  SizedBox(
                    height: 200,
                    child: VerticalDivider(
                      color: const Color.fromARGB(255, 121, 121, 121),
                      thickness: 1,
                      width: 3,
                    ),
                  ),
                  Container(
                    padding: EdgeInsets.all(10),

                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Container(
                          padding: EdgeInsets.only(left: 10),

                          width: 350,
                          color: Colors.transparent,
                          child: SingleChildScrollView(
                            scrollDirection: Axis.horizontal,
                            child: Row(
                              children: [
                                _buildImageCard(_getReadBooks()[1]),
                                _buildImageCard(_getWantToReadBooks()[2]),
                                _buildImageCard(_getWantToReadBooks()[3]),
                                _buildImageCard(_getReadBooks()[5]),
                                _buildImageCard(_getReadBooks()[2]),
                              ],
                            ),
                          ),
                        ),
                        RText(text: "Want to Reading", color: AppColor.white),
                      ],
                    ),
                  ),
SizedBox(
                    height: 200,
                    child: VerticalDivider(
                      color: const Color.fromARGB(255, 121, 121, 121),
                      thickness: 1,
                      width: 3,
                    ),
                  ),
                  Container(
                    padding: EdgeInsets.all(10),

                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Container(
                          padding: EdgeInsets.only(left: 10),

                          width: 350,
                          color: Colors.transparent,
                          child: SingleChildScrollView(
                            scrollDirection: Axis.horizontal,
                            child: Row(
                              children: [
                                _buildImageCard(_getReadBooks()[4]),
                                _buildImageCard(_getCurrentlyReadingBooks()[1]),
                                _buildImageCard(_getCurrentlyReadingBooks()[3]),
                                _buildImageCard(_getCurrentlyReadingBooks()[0]),
                                _buildImageCard(_getCurrentlyReadingBooks()[2]),
                              ],
                            ),
                          ),
                        ),
                        RText(text: "Read", color: AppColor.white),
                      ],
                    ),
                  ),
                ],
              ),
              Padding(
                padding: const EdgeInsets.only(top: 10.0, left: 30, right: 10),
                child: RText(
                  text: "My Movies",
                  size: 25,
                  color: AppColor.green,
                ),
              ),
              SizedBox(
                height: 30,
                child: Padding(
                  padding: const EdgeInsets.only(left: 30.0),
                  child: RText(
                    text: "No Movies Logged Yet.",
                    color: Color.fromARGB(255, 112, 112, 112),
                  ),
                ),
              ),
              Padding(
                padding: const EdgeInsets.only(top: 10.0, left: 30, right: 10),
                child: RText(text: "My Anime", size: 25, color: AppColor.green),
              ),
              SizedBox(
                height: 30,
                child: Padding(
                  padding: const EdgeInsets.only(left: 30.0),
                  child: RText(
                    text: "No Anime Logged Yet.",
                    color: Color.fromARGB(255, 112, 112, 112),
                  ),
                ),
              ),
              Padding(
                padding: const EdgeInsets.only(top: 10.0, left: 30, right: 10),
                child: RText(text: "My Games", size: 25, color: AppColor.green),
              ),
              SizedBox(
                height: 30,
                child: Padding(
                  padding: const EdgeInsets.only(left: 30.0),
                  child: RText(
                    text: "No Games Logged Yet.",
                    color: Color.fromARGB(255, 112, 112, 112),
                  ),
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }

  Widget _buildImageCard(String imageUrl) {
    return Padding(
      padding: const EdgeInsets.only(right: 10),
      child: ClipRRect(
        borderRadius: BorderRadius.circular(12),
        child: Image.network(
          imageUrl,
          width: 100,
          height: 130,
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
}
