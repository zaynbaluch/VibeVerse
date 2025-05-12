import 'package:flutter/material.dart';

// ignore: must_be_immutable
class SmallText extends StatelessWidget {
  Color? color;
  final String text;
  double size;
  double height;

  SmallText({
    super.key,
    required this.text,
    this.size = 12,
    this.color = const Color(0XFF000000),
    this.height = 1.8,
  });

  @override
  Widget build(BuildContext context) {
    return Text(
      text,
      textAlign: TextAlign.center,
      style: TextStyle(
        color: color,
        fontSize: size,
        fontWeight: FontWeight.normal,
        height: height,

        //fontFamily:'choosen font',
      ),
    );
  }
}
