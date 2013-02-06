/*
   SwingWT
   Copyright(c)2004, Daniel Naab

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: dannaab@users.sourceforge.net

*/
package swingwt.awt.color;

/**
 * Just stubbed out... don't even know where to start with this one.
 * For now, just has the static constants in..
 * @author Dan
 */
public class ICC_Profile
{
	public static final int CLASS_INPUT = 0;
	public static final int CLASS_DISPLAY = 1;
	public static final int CLASS_OUTPUT = 2;
	public static final int CLASS_DEVICELINK = 3;
	public static final int CLASS_COLORSPACECONVERSION = 4;
	public static final int CLASS_ABSTRACT = 5;
	public static final int CLASS_NAMEDCOLOR = 6;
	
	public static final int icSigXYZData = 0x58595A20;
	public static final int icSigLabData = 0x4C616220;
	public static final int icSigLuvData = 0x4C757620;
	public static final int icSigYCbCrData = 0x59436272;
	public static final int icSigYxyData = 0x59787920;
	public static final int icSigRgbData = 0x52474220;
	public static final int icSigGrayData = 0x47524159;
	public static final int icSigHsvData = 0x48535620;
	public static final int icSigHlsData = 0x484C5320;
	public static final int icSigCmykData = 0x434D594B;
	public static final int icSigCmyData = 0x434D5920;
	public static final int icSigSpace2CLR = 0x32434C52;
	public static final int icSigSpace3CLR = 0x33434C52;
	public static final int icSigSpace4CLR = 0x34434C52;
	public static final int icSigSpace5CLR = 0x35434C52;
	public static final int icSigSpace6CLR = 0x36434C52;
	public static final int icSigSpace7CLR = 0x37434C52;
	public static final int icSigSpace8CLR = 0x38434C52;
	public static final int icSigSpace9CLR = 0x39434C52;
	public static final int icSigSpaceACLR = 0x41434C52;
	public static final int icSigSpaceBCLR = 0x42434C52;
	public static final int icSigSpaceCCLR = 0x43434C52;
	public static final int icSigSpaceDCLR = 0x44434C52;
	public static final int icSigSpaceECLR = 0x45434C52;
	public static final int icSigSpaceFCLR = 0x46434C52;
	public static final int icSigInputClass = 0x73636E72;
	public static final int icSigDisplayClass = 0x6D6E7472;
	public static final int icSigOutputClass = 0x70727472;
	public static final int icSigLinkClass = 0x6C696E6B;
	public static final int icSigAbstractClass= 0x61627374;
	public static final int icSigColorSpaceClass = 0x73706163;
	public static final int icSigNamedColorClass = 0x6e6d636c;
	public static final int icPerceptual = 0;
	public static final int icRelativeColorimetric= 1;
	public static final int icSaturation = 2;
	public static final int icAbsoluteColorimetric= 3;
	public static final int icSigHead = 0x68656164;
	public static final int icSigAToB0Tag = 0x41324230;
	public static final int icSigAToB1Tag = 0x41324231;
	public static final int icSigAToB2Tag = 0x41324232;
	public static final int icSigBlueColorantTag = 0x6258595A;
	public static final int icSigBlueTRCTag= 0x62545243;
	public static final int icSigBToA0Tag = 0x42324130;
	public static final int icSigBToA1Tag = 0x42324131;
	public static final int icSigBToA2Tag = 0x42324132;
	public static final int icSigCalibrationDateTimeTag = 0x63616C74;
	public static final int icSigCharTargetTag = 0x74617267;
	public static final int icSigCopyrightTag = 0x63707274;
	public static final int icSigCrdInfoTag= 0x63726469;
	public static final int icSigDeviceMfgDescTag = 0x646D6E64;
	public static final int icSigDeviceModelDescTag = 0x646D6464;
	public static final int icSigDeviceSettingsTag = 0x64657673;
	public static final int icSigGamutTag = 0x67616D74;
	public static final int icSigGrayTRCTag = 0x6b545243;
	public static final int icSigGreenColorantTag = 0x6758595A;
	public static final int icSigGreenTRCTag = 0x67545243;
	public static final int icSigLuminanceTag = 0x6C756d69;
	public static final int icSigMeasurementTag = 0x6D656173;
	public static final int icSigMediaBlackPointTag = 0x626B7074;
	public static final int icSigMediaWhitePointTag = 0x77747074;
	public static final int icSigNamedColor2Tag= 0x6E636C32;
	public static final int icSigOutputResponseTag = 0x72657370;
	public static final int icSigPreview0Tag = 0x70726530;
	public static final int icSigPreview1Tag = 0x70726531;
	public static final int icSigPreview2Tag = 0x70726532;
	public static final int icSigProfileDescriptionTag = 0x64657363;
	public static final int icSigProfileSequenceDescTag = 0x70736571;
	public static final int icSigPs2CRD0Tag = 0x70736430;
	public static final int icSigPs2CRD1Tag = 0x70736431;
	public static final int icSigPs2CRD2Tag = 0x70736432;
	public static final int icSigPs2CRD3Tag = 0x70736433;
	public static final int icSigPs2CSATag = 0x70733273;
	public static final int icSigPs2RenderingIntentTag = 0x70733269;
	public static final int icSigRedColorantTag = 0x7258595A;
	public static final int icSigRedTRCTag = 0x72545243;
	public static final int icSigScreeningDescTag = 0x73637264;
	public static final int icSigScreeningTag = 0x7363726E;
	public static final int icSigTechnologyTag = 0x74656368;
	public static final int icSigUcrBgTag = 0x62666420;
	public static final int icSigViewingCondDescTag = 0x76756564;
	public static final int icSigViewingConditionsTag = 0x76696577;
	public static final int icSigChromaticityTag = 0x6368726d;
	public static final int icHdrSize = 0;
	public static final int icHdrCmmId = 4;
	public static final int icHdrVersion = 8;
	public static final int icHdrDeviceClass = 12;
	public static final int icHdrColorSpace = 16;
	public static final int icHdrPcs = 20;
	public static final int icHdrDate = 24;
	public static final int icHdrMagic = 36;
	public static final int icHdrPlatform = 40;
	public static final int icHdrFlags = 44;
	public static final int icHdrManufacturer = 48;
	public static final int icHdrModel = 52;
	public static final int icHdrAttributes= 56;
	public static final int icHdrRenderingIntent = 64;
	public static final int icHdrIlluminant = 68;
	public static final int icHdrCreator = 80;
	public static final int icTagType= 0;
	public static final int icTagReserved = 4;
	public static final int icCurveCount= 8;
	public static final int icCurveData = 12;
	public static final int icXYZNumberX = 8;
}
