import 'package:client/utils/colors.dart';
import 'package:client/utils/text.dart';
import 'package:flutter/material.dart';

class ExpandableText extends StatefulWidget {
  final String text;
  const ExpandableText({super.key, required this.text});

  @override
  State<ExpandableText> createState() => _ExpandableTextState();
}

class _ExpandableTextState extends State<ExpandableText> {
  late String firstHalf;
  late String secondHalf;
  bool hiddenText = true;

  double textHeight = 200;

  @override
  void initState() {
    super.initState();
    if (widget.text.length > textHeight) {
      firstHalf = widget.text.substring(0, textHeight.toInt());
      secondHalf = widget.text.substring(
        textHeight.toInt() + 1,
        widget.text.length,
      );
    } else {
      firstHalf = widget.text;
      secondHalf = "";
    }
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      child:
          secondHalf.isEmpty
              ? RText(
                text: firstHalf,
                color: Color.fromARGB(255, 255, 255, 255),
                size: 10,
              )
              : Column(
                children: [
                  RText(
                    color: Color.fromARGB(255, 255, 255, 255),
                    text:
                        hiddenText
                            ? ("$firstHalf...")
                            : (firstHalf + secondHalf),
                  ),
                  InkWell(
                    onTap: () {
                      setState(() {
                        hiddenText = !hiddenText;
                      });
                    },
                    child: Row(
                      children: [
                        RText(
                          text: hiddenText ? ("Show more") : ("Show less"),
                          color: AppColor.green,
                          size: 15,
                        ),
                        Icon(
                          hiddenText
                              ? (Icons.arrow_drop_down)
                              : (Icons.arrow_drop_up),
                          color: AppColor.green,
                        ),
                      ],
                    ),
                  ),
                ],
              ),
    );
  }
}
