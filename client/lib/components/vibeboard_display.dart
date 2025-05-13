import 'package:client/utils/colors.dart';
import 'package:client/utils/headings.dart';
import 'package:flutter/material.dart';

class VibeboardDisplay extends StatefulWidget {
  const VibeboardDisplay({super.key});

  @override
  State<VibeboardDisplay> createState() => _VibeboardDisplayState();
}

class _VibeboardDisplayState extends State<VibeboardDisplay> {
  @override
  Widget build(BuildContext context) {
    return Container(
      child: Row(
        children: [
          Container(
            width: 350,
            height: 350,
            padding: const EdgeInsets.all(15),

            decoration: BoxDecoration(
              color: AppColor.white,
              image: DecorationImage(
                image: AssetImage('images/vibeboard1.png'),
                fit: BoxFit.cover,
              ),
            ),
          ),
          SizedBox(width: 30),
          Container(
            width: 350,
            height: 350,
            padding: const EdgeInsets.all(15),

            decoration: BoxDecoration(
              color: AppColor.white,
              image: DecorationImage(
                image: AssetImage('images/vibeboard2.png'),
                fit: BoxFit.cover,
              ),
            ),
          ),
        ],
      ),
    );
  }
}
