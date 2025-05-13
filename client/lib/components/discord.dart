import 'package:client/utils/colors.dart';
import 'package:url_launcher/url_launcher.dart';
import 'package:flutter/material.dart';

class Discord extends StatefulWidget {
  @override
  State<Discord> createState() => _YourWidgetState();
}

class _YourWidgetState extends State<Discord> {
  bool linkClicked = false;
 

  Future<void> _launchURL() async {
  final Uri url = Uri.parse('https://discord.gg/TkAYMPvT');

  try {
    final bool launched = await launchUrl(
      url,
      mode: LaunchMode.platformDefault, // optional: LaunchMode.externalApplication
      webOnlyWindowName: '_blank', // for web only
    );

    if (!launched) {
      debugPrint('Could not launch $url');
    }
  } catch (e) {
    debugPrint('Error launching URL: $e');
  }
}

  @override
  Widget build(BuildContext context) {
    return TextButton(
      onPressed: _launchURL,
      child: Text(
        '💬 Vibe with us on Discord',
        style: TextStyle(
          color: AppColor.purple,
          decoration:
              linkClicked ? TextDecoration.underline : TextDecoration.none,
        ),
      ),
    );
  }
}
