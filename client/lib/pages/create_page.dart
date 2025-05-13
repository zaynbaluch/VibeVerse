import 'package:flutter/material.dart';
import 'package:client/main_scaffold.dart';
import 'package:client/utils/colors.dart';
import 'package:client/utils/headings.dart';

class CreatePage extends StatefulWidget {
  const CreatePage({super.key});

  @override
  State<CreatePage> createState() => _CreatePageState();
}

class _CreatePageState extends State<CreatePage> {
  @override
  Widget build(BuildContext context) {
    return MainScaffold(
      body: Container(
        padding: const EdgeInsets.all(16),
        constraints: BoxConstraints(
          minHeight: MediaQuery.of(context).size.height,
        ),
        child: SingleChildScrollView(
          child: Center(
            child: HeaderText(text: "CreatePage", color: AppColor.white),
          ),
        ),
      ),
      currentRoute: 'create',
      onNavigate: (route) {
        Navigator.pushReplacementNamed(context, '/$route');
      },
    );
  }
}
