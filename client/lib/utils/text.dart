import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

// ignore: must_be_immutable
class RText extends StatelessWidget {
  Color? color;
  final String text;
  double size;
  double height;

  RText({
    super.key,
    required this.text,
    this.size = 20,
    this.color = const Color(0XFF000000),
    this.height = 1.8,
  });

  @override
  Widget build(BuildContext context) {
    return Text(
      text,
      style: GoogleFonts.bebasNeue(
        color: color,
        fontSize: size,
        fontWeight: FontWeight.normal,
        height: height,

       
      ),
    );
  }
}
