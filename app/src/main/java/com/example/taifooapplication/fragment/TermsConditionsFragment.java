package com.example.taifooapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.taifooapplication.R;

public class TermsConditionsFragment extends Fragment {

    TextView datadetails;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aboutas,container,false);

        datadetails = view.findViewById(R.id.datadetails);

        datadetails.setText("Please read the terms of this Term of Service Agreement (\"Website\") carefully. By using the website – www.freshshoppe.in (\"Website\"), or the taifoo E mobile application on iOS or Android devise including phones, tablets or any other electronic devise (\"FRESH SHOPPE APP\"), or the mobile site, www.taifoo.in (\"Msite\"), whereas the Website, the freshshoppe App and the M-site are together referred to as (the \"Platform\"), or purchasing products from the Platform you agree to be bound by all of the terms and conditions of this Agreement.\n" +
                "\n" +
                "This Agreement governs your use of this Platform,  offer of products for purchase on this Platform, and / or your purchase of products available on this Platform.\n" +
                "\n" +
                "For the purposes of this Agreement, wherever the context so requires \"You\" or \"User\" shall mean any person including any natural or legal person who uses the Platform or buys any product from the Platform using any computer systems or other devices including but not limited to any mobile device, handheld device and tablets.\n" +
                "\n" +
                "This Agreement includes, and incorporates by reference, the policies and guidelines referred to in this Agreement. In the event of any conflict between the terms of this Agreement and any provision of such polices and guidelines, the terms of this Agreement shall prevail in relation to the subject matter hereof and the terms of such polices and guidelines shall prevail in relation to the subject matter thereof. You are strongly advised to carefully read all such policies and guidelines, as available on the Platform.\n" +
                "\n" +
                "A2R Retail reserves the right to amend the Agreement at any time by posting such amendments or the amended Agreement on this Platform.\n" +
                "\n" +
                "A2R Retail may alert you of the amendments by indicating on the top of this Agreement the date it was last revised or by any other means. The amended Agreement will be effective immediately after it is posted on this Platform. Your use of the Platform following the posting of any such amendments will constitute continued acceptance of the Agreement including the amendments thereof.\n" +
                "\n" +
                "A2R Retail encourages you to review this Agreement whenever you visit the Platform to make sure that you understand the terms and conditions governing the use of the Platform and / or the purchase of products. This Agreement does not alter in any way the terms or conditions of any other written agreement you may have with A2R Retail for other products or services. In the event of any conflict between the terms of this Agreement and any other agreement executed with you, the terms of such other agreement shall prevail in relation to the subject matter thereof.\n" +
                "\n" +
                "If you do not agree with this Agreement (including any policies or guidelines referred to herein), please immediately terminate your use of the Platform. If you would like to print this Agreement, please click the print button on your browser toolbar.\n" +
                "\n" +
                "PRODUCTS\n" +
                "\n" +
                "Terms of Offer\n" +
                "\n" +
                "This Platform offers for sale various products including food products (the \"Products\"). By placing an order for Products through this Platform, you agree to the terms set forth in this Agreement.\n" +
                "\n" +
                "Customer Solicitation\n" +
                "\n" +
                "Unless you notify in writing our third party vendors or A2R Retail advisors, of your intention to opt out from further direct and indirect marketing communications and solicitations, you are agreeing to continue to receive emails, calls and messages through other mediums which A2R Retail may adopt from time to time including but not limited to text messages for soliciting Products.\n" +
                "\n" +
                "Opt Out Procedure\n" +
                "\n" +
                "You may choose to opt out from receiving future marketing and solicitation communications by following the steps below.\n" +
                "\n" +
                "1. You may also choose to opt out, via sending your email address to: freshshoppe.india@gmail.com.\n" +
                "\n" +
                "2. You may send a written remove request to A2R RETAIL B6 Saheed Nagar,Bhubaneswar,Odisha  Indian– 751007\n" +
                "\n" +
                "Proprietary Rights\n" +
                "\n" +
                "A2R RETAIL has proprietary rights and trade secrets in the Products. You shall not copy, reproduce, resell or redistribute any Product manufactured and/or distributed by A2R RETAIL .We also have rights to all trademarks and trade dress and specific layouts of this Webpage, including calls to action, text placement, images and other information.\n" +
                "\n" +
                "PLATFORM\n" +
                "\n" +
                "Content, Intellectual Property, and Third Party Links\n" +
                "\n" +
                "In addition to making Products available, this Platform also offers information and marketing materials. This Platform also offers information, both directly and through indirect links to third-party websites, mobile application about nutritional and dietary supplements. A2R RETAIL does not always create the information offered on this Platform; instead the information is often gathered from other sources. To the extent that A2R RETAIL does create the content on this Platform, such content is protected by intellectual property laws of the India, foreign nations, and international bodies. Unauthorized use of the material may violate copyright, trademark, and/or other laws.\n" +
                "\n" +
                "You acknowledge that your use of the content on this Platform is for personal, non-commercial use. Any links to third-party websites and mobile applications are provided solely as a convenience to you. A2R RETAIL does not endorse the contents on any such third-party websites and mobile applications. A2R RETAIL is not responsible for the content of or any damage that may result from your access to or reliance on these third-party websites and mobile applications. If you link to third-party websites, you do so at your own risk.\n" +
                "\n" +
                "Eligibility\n" +
                "\n" +
                "Persons who can form legally binding contracts under Indian Contract Act, 1872 (Act) are only eligible to use the Platform. Persons who are \"incompetent to contract\" within the meaning of the Act are not eligible to use the Platform. If you are under the age of 18 years (minor), you shall not register as a user and shall not transact on or use the Platform. Legal guardian or parents can transact or use the Platform on behalf of minor. A2R RETAIL reserves the right to terminate your membership and/or refuse access to the Platform upon acquiring actual knowledge that you are under the age of 18 years. Use of Platform; A2R RETAIL is not responsible for any damages resulting from use of this Platform by anyone. You will not use the Platform for illegal purposes. You will (1) abide by all applicable local, state, national, and international laws and regulations in your use of the Platform (including laws regarding intellectual property), (2) not interfere with or disrupt the use and enjoyment of the Platform by other users, (3) not resell material on the Platform, (4) not engage, directly or indirectly, in transmission of \"spam\", chain letters, junk mail or any other type of unsolicited communication, and (5) not defame, harass, abuse, or disrupt other users of the Platform License. By using this Platform, you are granted a limited, non-exclusive, non-transferable right to use the content and materials on the Platform in connection with your normal, non-commercial, use of the Platform. You may not copy, reproduce, transmit, distribute, or create derivative works of such content or information without express written authorization from A2R RETAIL or the applicable third party (if third party content is at issue).\n" +
                "\n" +
                "Posting\n" +
                "\n" +
                "By posting, storing, or transmitting any content on the Platform, you hereby grant A2R RETAIL a perpetual, worldwide, non-exclusive, royalty-free, assignable, right and license to use, copy, display, perform, create derivative works from, distribute, have distributed, transmit and assign such content in any form, in all media now known or hereinafter created, anywhere in the world. A2R RETAIL does not have the ability to control the nature of the user-generated content offered through the Platform. You are solely responsible for your interactions with other users of the Platform and any content you post. A2R RETAIL is not liable for any damage or harm resulting from any posts by or interactions between users. A2R RETAIL reserves the right, but has no obligation, to monitor interactions between and among users of the Platform and to remove any content A2R RETAIL deems objectionable, at its sole discretion.\n" +
                "\n" +
                "DISCLAIMER OF WARRANTIES\n" +
                "\n" +
                "Your use of this Platform and/or products are at your sole risk. The Platform and Products are offered on an \"as is\" and \"as available\" basis. To the extent permitted under applicable laws, A2R RETAIL expressly disclaims all warranties of any kind, whether express or implied, including, but not limited to, implied warranties of merchantability, fitness for a particular purpose and non-infringement with respect to the Products or Platform contents, or any reliance upon or use of the Platform content or Products.\n" +
                "\n" +
                "Without limiting the generality of the foregoing, A2R RETAIL makes no warranty: That the information provided on this Platform is accurate, reliable, complete, or timely. The links to third-party Platforms are to information that is accurate, reliable, complete, and timely.\n" +
                "\n" +
                "No advice or information, whether oral or written, obtained by you from this Platform will create any warranty not expressly stated herein as to the results that may be obtained from the use of the Products or that defects in products will be corrected.\n" +
                "\n" +
                "LIMITATION OF LIABILITY\n" +
                "\n" +
                "The entire liability of A2R RETAIL and your exclusive remedy, in law, in equity, or otherwise, with respect to the Platform content and Products and/or for any breach of this Agreement is solely limited to the amount you paid in last one month from any such claim arising, less shipping and handling costs and taxes, for Products purchased via the Platform.\n" +
                "\n" +
                "To the extent permitted under applicable laws, A2R RETAIL will not be liable for any direct, indirect, incidental, special or consequential damages in connection with this Agreement or the Products in any manner, including liabilities resulting from (1) the use or the inability to use the Platform content or Products; (2) the cost of procuring substitute Products or content; (3) any Products purchased or obtained or transactions entered into through the Platform; or (4) any lost profits you allege.\n" +
                "\n" +
                "INDEMNIFICATION\n" +
                "\n" +
                "You will release, indemnify, defend and hold harmless A2R RETAIL and any of its contractors, agents, employees, officers, directors, shareholders, affiliates and assigns from all liabilities, claims, damages, costs and expenses, including reasonable attorneys' fees and expenses, of third parties relating to or arising out of (1) this Agreement or the breach of your warranties, representations and obligations under this Agreement; (2) the Platform content or your use of the Platform content; (3) the Products or your use of the Products ; (4) any intellectual property or other proprietary right of any person or entity; (5) your violation of any provision of this Agreement; or (6) any information or data you supplied to A2R RETAIL. When A2R RETAIL is threatened with suit or sued by a third party, A2R RETAIL may seek written assurances from you concerning your promise to indemnify A2R RETAIL; your failure to provide such assurances may be considered by A2R RETAIL to be a material breach of this Agreement. A2R RETAIL will have the right to participate in any defence by you of a third-party claim related to your use of any of the Platform content or Products, with counsel of A2R RETAIL choice at its expense. A2R RETAIL will reasonably cooperate in any defence by you of a third-party claim at your request and expense. You will have sole responsibility to defend A2R RETAIL against any claim, but you must receive A2R RETAIL prior written consent regarding any related settlement. The terms of this provision will survive any termination or cancellation of this Agreement or your use of the Platform or Products.\n" +
                "\n" +
                "ORDERING AND PRICING\n" +
                "\n" +
                "The quantity of any Product ordered by you and the quantity of Product supplied to you may vary. Please note that while we take utmost care to supply exact quantity of Product ordered by you, due to the nature of Products, it is not possible that the exact quantity of Product that you have ordered will be supplied.\n" +
                "\n" +
                "The price displayed on the Platform is in relation to the particular quantity of Product as provided on the Platform and against such Product. Therefore, the price displayed for your order will also be on the basis of the weight / description of the Product ordered by you. However, the actual amount charged to you will be on the basis of the actual amount / quantity of Product delivered to you. Any difference in estimated price and actual price shall be adjusted by making a refund by A2R RETAIL or payment of difference by you to A2R RETAIL. Any excess amount paid by you shall be refunded to you and the mode of refund shall be as determined by A2R RETAIL from time to time, such as, credit to Fresh Shoppe wallet, credit through Fresh Shoppe points, crediting any other mobile wallet that you may use. Any additional amount that you are liable to pay, shall be collected at the time of delivery of the Product.\n" +
                "\n" +
                "DELIVERY, HANDLING CHARGES AND TAXES\n" +
                "\n" +
                "A2R RETAIL may charge such delivery and handling charges as it may in its sole discretion determine from time to time and applicable taxes. At present, A2R RETAIL charges Rs. 35 (Rupees Thirty Five Only) inclusive of delivery and, handling charges and taxes, if applicable. A2R RETAIL may in its sole and absolute discretion, without giving any prior notice, may revise such all-inclusive charge.\n" +
                "\n" +
                "MODIFICATION\n" +
                "\n" +
                "Prices for our Products are subject to change without notice. We reserve the right at any time to modify or discontinue the Products (or any part or content thereof) without notice at any time. We shall not be liable to you or to any third-party for any modification, price change, suspension or discontinuance of the Products.\n" +
                "\n" +
                "ACCURACY OF BILLING AND ACCOUNT INFORMATION\n" +
                "\n" +
                "A2R RETAIL reserve the right to refuse any order you place with us. We may, at our sole discretion, limit or cancel quantities purchased per person, per household or per order. These restrictions may include orders placed by or under the same customer account, the same credit card, and/or orders that use the same billing and/or shipping address. In the event that we make a change to or cancel an order, we may attempt to notify you by contacting the e-mail and/or billing address/phone number provided at the time the order was made. You agree to provide current, complete and accurate purchase and account information for all purchases made at Platform.\n" +
                "\n" +
                "PRIVACY\n" +
                "\n" +
                "A2R RETAIL believes strongly in protecting user privacy and providing you with notice of use of your data. Please refer to A2R RETAIL privacy policy, incorporated by reference Privacy Policy. The privacy policy can also be accessed on the Platform.\n" +
                "\n" +
                "AGREEMENT TO BE BOUND\n" +
                "\n" +
                "By using this Platform or ordering Products, you acknowledge that you have read and agree to be bound by this Agreement and all terms and conditions on this Platform.\n" +
                "\n" +
                "GENERAL\n" +
                "\n" +
                "Force Majeure\n" +
                "\n" +
                "A2R RETAIL will not be deemed in default hereunder or held responsible for any cessation, interruption or delay in the performance of its obligations hereunder due to earthquake, flood, fire, storm, natural disaster, act of God, war, terrorism, armed conflict, labour strike, lockout, or boycott.\n" +
                "\n" +
                "Cessation of Operation\n" +
                "\n" +
                "A2R RETAIL may at any time, in its sole discretion and without advance of the Platform and distribution of the Products.\n" +
                "\n" +
                "Entire Agreement\n" +
                "\n" +
                "This Agreement including the policies and guidelines included herein by reference comprise the entire agreement between you and A2R RETAIL and supersedes any prior agreements pertaining to the subject matter contained herein.\n" +
                "\n" +
                "Effect of Waiver\n" +
                "\n" +
                "The failure of A2R RETAIL to exercise or enforce any right or provision of this Agreement will not constitute a waiver of such right or provision. If any provision of this Agreement is found by a court of competent jurisdiction to be invalid, the parties nevertheless agree that the court should endeavour to give effect to the parties' intentions as reflected in the provision, and the other provisions of this Agreement remain in full force and effect.\n" +
                "\n" +
                "Governing Law and Jurisdiction\n" +
                "\n" +
                "This Platform originates from the Bhubaneswar, Odisha. This Agreement will be governed by the laws of India and shall be subject to the exclusive jurisdiction of Courts in the State of Odisha.\n" +
                "\n" +
                "Waiver of Class Action Rights\n" +
                "\n" +
                "By entering into this agreement, you hereby irrevocably waive any right you may have to join claims with those of other in the form of a class action or similar procedural device. Any claims arising out of, relating to, or connection with this agreement must be asserted individually.\n" +
                "\n" +
                "Termination\n" +
                "\n" +
                "A2R RETAIL reserves the right to terminate your access to the Platform without advance notice if it reasonably believes, in its sole discretion, that you have breached any of the terms and conditions of this Agreement. Following termination, you will not be permitted to use the Platform and A2R RETAIL may, in its sole discretion and without advance notice to you, cancel any outstanding orders for Products. If your access to the Platform is terminated, A2R RETAIL reserves the right to exercise whatever means it deems necessary to prevent unauthorized access of the Platform. This Agreement will survive indefinitely unless and until A2R RETAIL chooses, in its sole discretion and without advance to you, to terminate it.\n" +
                "\n" +
                "Domestic Use\n" +
                "\n" +
                "A2R RETAIL makes no representation that the Platform or Products are appropriate or available for use in locations outside India. Users who access the Platform from outside India do so at their own risk and initiative and must bear all responsibility for compliance with any applicable local laws.\n" +
                "\n" +
                "Assignment\n" +
                "\n" +
                "You may not assign your rights and obligations under this Agreement to anyone. A2R RETAIL may assign its rights and obligations under this Agreement in its sole discretion and without advance notice to you.\n" +
                "\n" +
                "Severability\n" +
                "\n" +
                "In the event that any provision of this Agreement is determined to be unlawful, void or unenforceable, such provision shall nonetheless be enforceable to the fullest extent permitted by applicable law, and the unenforceable portion shall be deemed to be severed from the Agreement, such determination shall not affect the validity and enforceability of any other remaining provisions.\n" +
                "\n" +
                "Contact Us\n" +
                "\n" +
                "Please contact us for any questions or comments (including all inquiries unrelated to copyright infringement) regarding this Platform.\n" +
                "\n" +
                "Grievance officer\n" +
                "\n" +
                "In accordance with Information Technology Act, 2000 and rules made there under, the name and contact details of the Grievance Officer are provided below:\n" +
                "\n" +
                "Mr.Wasef hasan\n" +
                "\n" +
                " Mr.Taif hasan\n" +
                "\n" +
                "A2R RETAIL\n" +
                "\n" +
                "Email: taifoo@gmail.com\n" +
                "\n" +
                "\n" +
                "Time: Mon - Fri (9:00 - 18:00)\n" +
                "\n" +
                "BY USING THIS PLATFORM OR ORDERING PRODUCTS FROM THIS PLATFORM YOU AGREE TO BE BOUND BY ALL OF THE TERMS AND CONDITIONS OF THIS AGREEMENT.");




        return view;
    }
}
