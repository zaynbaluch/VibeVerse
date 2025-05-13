import 'package:client/main_scaffold.dart';
import 'package:client/utils/colors.dart';
import 'package:client/utils/headings.dart';
import 'package:client/utils/text.dart';
import 'package:flutter/material.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  Widget build(BuildContext context) {
    return MainScaffold(
      body: SingleChildScrollView(
        child: Container(
          padding: const EdgeInsets.all(16),
          constraints: BoxConstraints(
            minHeight: MediaQuery.of(context).size.height,
          ),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [RText(text: "Home", color: AppColor.white, size: 80)],
          ),
        ),
      ),
      currentRoute: 'home',
      onNavigate: (route) {
        Navigator.pushReplacementNamed(context, '/$route');
      },
    );
  }
}
