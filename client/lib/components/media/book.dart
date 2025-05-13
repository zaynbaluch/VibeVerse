import 'package:client/main_scaffold.dart';
import 'package:client/utils/colors.dart';
import 'package:client/utils/expandable_text.dart';
import 'package:client/utils/text.dart';
import 'package:flutter/material.dart';

class Book extends StatefulWidget {
  const Book({super.key});

  @override
  State<Book> createState() => _BookState();
}

class _BookState extends State<Book> {
  @override
  Widget build(BuildContext context) {
    return MainScaffold(
      currentRoute: 'book',
      onNavigate: (route) {
        Navigator.pushReplacementNamed(context, '/$route');
      },
      body: SingleChildScrollView(
        child: Container(
          padding: const EdgeInsets.all(16),
          constraints: BoxConstraints(
            minHeight: MediaQuery.of(context).size.height,
          ),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Row(
                children: [
                  IconButton(
                    icon: Icon(
                      Icons.arrow_back,
                      color: AppColor.purple, // Set the color here
                    ),
                    onPressed: () {
                      // Navigator.pop(context);
                      Navigator.pushNamed(context, '/explore');
                    },
                  ),
                  RText(
                    text: "The Little Prince",
                    color: AppColor.white,
                    size: 80,
                  ),
                  SizedBox(width: 20),
                  RText(
                    text: "By Antoine de Saint-Exupéry",
                    color: Color.fromARGB(255, 121, 121, 121),
                    size: 40,
                  ),
                ],
              ),
              Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                mainAxisAlignment: MainAxisAlignment.start,
                children: [
                  _buildImageCard(
                    'https://m.media-amazon.com/images/I/71OZY035QKL._AC_UF1000,1000_QL80_.jpg',
                  ),
                  SizedBox(width: 30),
                  Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    mainAxisAlignment: MainAxisAlignment.start,
                    children: [
                      Row(
                        children: [
                          ...List.generate(5, (index) {
                            return Icon(
                              index < 4
                                  ? Icons.star
                                  : Icons.star_border, // 4 filled, 1 empty
                              color: Colors.amber,
                              size: 24,
                            );
                          }),
                          const SizedBox(
                            width: 8,
                          ), // spacing between stars and text
                          RText(text: "4.0", color: AppColor.white),
                        ],
                      ),

                      RText(text: "DESCRIPTION:", color: AppColor.purple),
                      Container(
                        width: 730,
                        child: ExpandableText(
                          text:
                              "A pilot stranded in the desert awakes one morning to see, standing before him, the most extraordinary little fellow. \"Please,\" asks the stranger, \"draw me a sheep.\" And the pilot realizes that when life's events are too difficult to understand, there is no choice but to succumb to their mysteries. He pulls out pencil and paper... And thus begins this wise and enchanting fable that, in teaching the secret of what is really important in life, has changed forever the world for its readers. Few stories are as widely read and as universally cherished by children and adults alike as The Little Prince, presented here in a stunning new translation with carefully restored artwork. The definitive edition of a worldwide classic, it will capture the hearts of readers of all ages.",
                        ),
                      ),
                      Row(
                        children: [
                          RText(
                            text: "Genre:",
                            color: AppColor.purple,
                            size: 15,
                          ),
                          RText(
                            text:
                                "   Classics, Fiction, Fantasy, Childrens, France, Philosophy ...more",
                            color: Color.fromARGB(255, 147, 147, 147),
                            size: 15,
                          ),
                        ],
                      ),
                      Row(
                        children: [
                          RText(
                            text: "Pages:",
                            color: AppColor.purple,
                            size: 15,
                          ),
                          RText(
                            text: "  96",
                            color: Color.fromARGB(255, 147, 147, 147),
                            size: 15,
                          ),
                        ],
                      ),
                      SizedBox(
                        width: 300,
                        child: ElevatedButton(
                          onPressed: () {
                            // Your action here
                          },
                          style: ElevatedButton.styleFrom(
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(7),
                            ),
                            backgroundColor:
                                AppColor.green, // Optional: customize color
                            foregroundColor: Colors.white,
                          ),
                          child: RText(
                            text: "Want to Read",
                            color: Colors.black,
                          ),
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}

Widget _buildImageCard(String imageUrl) {
  return Padding(
    padding: const EdgeInsets.only(right: 10, left: 40),
    child: Material(
      elevation: 30,
      borderRadius: BorderRadius.circular(12),

      child: ClipRRect(
        borderRadius: BorderRadius.circular(12),
        child: Image.network(
          imageUrl,
          width: 270,
          height: 350,
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
    ),
  );
}
