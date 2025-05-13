import 'package:client/utils/colors.dart';
import 'package:client/utils/headings.dart';
import 'package:client/utils/text.dart';
import 'package:flutter/material.dart';

class StartingPage extends StatelessWidget {
  const StartingPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        child: Column(
          children: [
            Container(
              height: 70,
              width: double.infinity,
              color: AppColor.green,
              padding: const EdgeInsets.symmetric(horizontal: 16.0),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Row(
                    children: [
                      Image.asset(
                        'images/logo.png',
                        width: 60,
                        fit: BoxFit.cover,
                      ),
                      HeaderText(text: "VIBEVERSE", color: AppColor.black),
                    ],
                  ),
                  ElevatedButton(
                    onPressed: () {
                      Navigator.pushNamed(context, '/landing');
                    },
                    style: ElevatedButton.styleFrom(
                      backgroundColor: AppColor.black,
                      foregroundColor: AppColor.green,
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(7),
                      ),
                    ),
                    child: RText(text: "Join the vibe", color: AppColor.white),
                  ),
                ],
              ),
            ),

            // FIXED STACK
            SizedBox(
              height: 700, // Provide fixed height for proper stacking
              child: Stack(
                children: [
                  Positioned.fill(
                    child: Image.asset('images/backg.png', fit: BoxFit.cover),
                  ),
                  Positioned(
                    top: 360,
                    left: 540,
                    child: Center(
                      child: SizedBox(
                        width: 200, // set appropriate width
                        height: 70,
                        child: ElevatedButton(
                          onPressed: () {
                            Navigator.pushNamed(context, '/landing');
                          },
                          style: ElevatedButton.styleFrom(
                            backgroundColor: AppColor.purple,
                            foregroundColor: Colors.white,
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(7),
                            ),
                            padding: const EdgeInsets.symmetric(
                              horizontal: 24,
                              vertical: 12,
                            ),
                          ),
                          child: RText(
                            text: "START VIBING",
                            color: AppColor.white,
                            size: 35,
                          ),
                        ),
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
