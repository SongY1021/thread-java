package com.huashi.otg;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.huashi.otg.sdk.HSIDCardInfo;
import com.huashi.otg.sdk.HandlerMsg;
import com.huashi.otg.sdk.HsOtgApi;
import com.huashi.otg.sdk.Test;

import java.io.*;
import java.text.SimpleDateFormat;

public class MainActivity_new extends Activity {
	
	private TextView sam, tv_info, statu;
	private ImageView iv_photo;
	private Button conn, read,autoread;
	boolean m_Auto = false;
	HsOtgApi api;
	String filepath="";
	SimpleDateFormat df = new SimpleDateFormat("yyyy��MM��dd��");// �������ڸ�ʽ
	
	Handler h = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 99 || msg.what == 100) {
				statu.setText((String)msg.obj);
			}
			//��һ����Ȩʱ����ж�������handler�жϣ���Ȩ����Ͳ�������ж���
			if (msg.what ==HandlerMsg.CONNECT_SUCCESS) {
				statu.setText("���ӳɹ�");
				sam.setText(api.GetSAMID());
			}
			if (msg.what == HandlerMsg.CONNECT_ERROR) {
				statu.setText("����ʧ��");
			}
			if (msg.what == HandlerMsg.READ_ERROR) {
				//cz();
				statu.setText("����֤ʧ��");
			}
			if (msg.what == HandlerMsg.READ_SUCCESS) {
				statu.setText("�����ɹ�");
				HSIDCardInfo ic = (HSIDCardInfo) msg.obj;
				byte[] fp = new byte[1024];
                fp = ic.getFpDate();
                String m_FristPFInfo = "";
                String m_SecondPFInfo = "";

                if (fp[4] == (byte)0x01) {
                    m_FristPFInfo = String.format("ָ��  ��Ϣ����һöָ��ע��ɹ���ָλ��%s��ָ��������%d \n", GetFPcode(fp[5]), fp[6]);
                } else {
                    m_FristPFInfo = "���֤��ָ�� \n";
                }
                if (fp[512 + 4] == (byte)0x01) {
                    m_SecondPFInfo = String.format("ָ��  ��Ϣ���ڶ�öָ��ע��ɹ���ָλ��%s��ָ��������%d \n", GetFPcode(fp[512 + 5]),
                            fp[512 + 6]);
                } else {
                    m_SecondPFInfo = "���֤��ָ�� \n";
                }
				if (ic.getcertType() == " ") {
					tv_info.setText("֤�����ͣ����֤\n" + "������"
							+ ic.getPeopleName() + "\n" + "�Ա�" + ic.getSex()
							+ "\n" + "���壺" + ic.getPeople() + "\n" + "�������ڣ�"
							+ df.format(ic.getBirthDay()) + "\n" + "��ַ��"
							+ ic.getAddr() + "\n" + "��ݺ��룺" + ic.getIDCard()
							+ "\n" + "ǩ�����أ�" + ic.getDepartment() + "\n"
							+ "��Ч���ޣ�" + ic.getStrartDate() + "-"
							+ ic.getEndDate() + "\n" + m_FristPFInfo + "\n"
							+ m_SecondPFInfo);
				} else {
					if(ic.getcertType() == "J")
					{
					tv_info.setText("֤�����ͣ��۰�̨��ס֤��J��\n" 
							+ "������" + ic.getPeopleName() + "\n" + "�Ա�"
							+ ic.getSex() + "\n"
							+ "ǩ��������" + ic.getissuesNum() + "\n"
							+ "ͨ��֤���룺" + ic.getPassCheckID() + "\n"
							+ "�������ڣ�" + df.format(ic.getBirthDay())
							+ "\n" + "��ַ��" + ic.getAddr() + "\n" + "��ݺ��룺"
							+ ic.getIDCard() + "\n" + "ǩ�����أ�"
							+ ic.getDepartment() + "\n" + "��Ч���ޣ�"
							+ ic.getStrartDate() + "-" + ic.getEndDate() + "\n"
							+ m_FristPFInfo + "\n" + m_SecondPFInfo);
					}
					else{
						if(ic.getcertType() == "I")
						{
						tv_info.setText("֤�����ͣ���������þ���֤��I��\n" 
								+ "Ӣ�����ƣ�" + ic.getPeopleName() + "\n" 
								+ "�������ƣ�" + ic.getstrChineseName() + "\n" 
								+ "�Ա�" + ic.getSex() + "\n"
								+ "���þ���֤�ţ�" + ic.getIDCard() + "\n"
								+ "������" + ic.getstrNationCode() + "\n"
								+ "�������ڣ�" + df.format(ic.getBirthDay())
								+ "\n" + "֤���汾�ţ�" + ic.getstrCertVer() + "\n" 
								+ "����������أ�" + ic.getDepartment() + "\n" 
								+ "��Ч���ޣ�"+ ic.getStrartDate() + "-" + ic.getEndDate() + "\n"
								+ m_FristPFInfo + "\n" + m_SecondPFInfo);
						}
					}

				}
				Test.test("/mnt/sdcard/test.txt4", ic.toString());
				try {
                    int ret = api.Unpack(filepath, ic.getwltdata());// ��Ƭ����
                    Test.test("/mnt/sdcard/test3.txt", "������");
                    if (ret != 0) {// ����ʧ��
                        return;
                    }
                    FileInputStream fis = new FileInputStream(filepath + "/zp.bmp");
                    Bitmap bmp = BitmapFactory.decodeStream(fis);
                    fis.close();
                    iv_photo.setImageBitmap(bmp);
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "ͷ�񲻴��ڣ�", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    // TODO �Զ����ɵ� catch ��
                    Toast.makeText(getApplicationContext(), "ͷ���ȡ����", Toast.LENGTH_SHORT).show();
                }catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "ͷ�����ʧ��", Toast.LENGTH_SHORT).show();
                }
				
			}
		};
	};
	
	/**
     * �ж��Ƿ�ӵ��Ȩ��
     *
     * @param permissions
     * @return
     */
//    public boolean hasPermission(String... permissions) {
//        for (String permission : permissions) {
//            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
//                return false;
//        }
//        return true;
//    }
//    
//    /**
//     * ����Ȩ��
//     */
//    protected void requestPermission(int code, String... permissions) {
//        ActivityCompat.requestPermissions(this, permissions, code);
//        ToastUtil.showMessage(this, "����ܾ���Ȩ,�ᵼ��Ӧ���޷�����ʹ��", Toast.Length_SHORT);
//    }
//	
//    /**
//     * ����Ȩ�޵Ļص�
//     *
//     * @param requestCode
//     * @param permissions
//     * @param grantResults
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case Constants.CODE_CAMERA:
//            //���ӣ���������Ļص�
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    ToastUtil.showMessage(this, "������ӵ����Ȩ��");
//                    // ����д����Ҫ��ҵ���߼�
//                    doYourNeedDo();
//                } else {
//                    ToastUtil.showMessage(this, "���ܾ���Ȩ,�ᵼ��Ӧ���޷�����ʹ�ã�������ϵͳ���������¿���Ȩ��", Toast.Length_SHORT);
//                }
//                break;
//            case Constants.CODE_READ_EXTERNAL_STORAGE:
//            //��һ��Ȩ�޵Ļص�
//                break;
//        }
//    }

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wltlib";// ��ȨĿ¼
	//	filepath = "/mnt/sdcard/wltlib";// ��ȨĿ¼
		Log.e("LJFDJ", filepath);
		onConn();
	}

	private void onConn(){
		copy(MainActivity_new.this, "base.dat", "base.dat", filepath);
		copy(MainActivity_new.this, "license.lic", "license.lic", filepath);
		api = new HsOtgApi(h, MainActivity_new.this);
		int ret = api.init();// ��Ϊ��һ����Ҫ�����Ȩ�����Ե�һ�ε��ʱ��ķ�����-1�����������˹㲥���ܵ���Ȩ����handler������Ϣ
		if (ret == 1) {
			new Thread(new CPUThread()).start();
		} else {
			Log.e("����ʧ��");
		}
	}

	private void cz() {
		// TODO Auto-generated method stub
		tv_info.setText("");
		iv_photo.setImageBitmap(null);
	}
	
	public class CPUThread extends Thread {
		public CPUThread() {
			super();
		}
		@Override
		public void run() {
			super.run();
			HSIDCardInfo ici;
			Message msg;
			while (m_Auto) {
				if (api.Authenticate(200, 200) != 1) {
					msg = Message.obtain();
					msg.what = HandlerMsg.READ_ERROR;
					h.sendMessage(msg);
				} else {
					ici = new HSIDCardInfo();
					if (api.ReadCard(ici, 200, 1300) == 1) {
						msg = Message.obtain();
						msg.obj = ici;
						msg.what = HandlerMsg.READ_SUCCESS;
						h.sendMessage(msg);
					}
				}
				SystemClock.sleep(300);
			}

		}
	}
	
	private void copy(Context context, String fileName, String saveName,
			String savePath) {
		File path = new File(savePath);
		if (!path.exists()) {
			path.mkdir();
		}

		try {
			File e = new File(savePath + "/" + saveName);
			if (e.exists() && e.length() > 0L) {
				Log.i("LU", saveName + "������");
				return;
			}

			FileOutputStream fos = new FileOutputStream(e);
			InputStream inputStream = context.getResources().getAssets()
					.open(fileName);
			byte[] buf = new byte[1024];
			boolean len = false;

			int len1;
			while ((len1 = inputStream.read(buf)) != -1) {
				fos.write(buf, 0, len1);
			}

			fos.close();
			inputStream.close();
		} catch (Exception var11) {
			Log.i("LU", "IO�쳣");
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (api == null) {
			return;
		}
		api.unInit();
	}

	 /**
     * ָ�� ָλ����
     *
     * @param FPcode
     * @return
     */
    String GetFPcode(int FPcode) {
        switch (FPcode) {
            case 11:
                return "����Ĵָ";
            case 12:
                return "����ʳָ";
            case 13:
                return "������ָ";
            case 14:
                return "���ֻ�ָ";
            case 15:
                return "����Сָ";
            case 16:
                return "����Ĵָ";
            case 17:
                return "����ʳָ";
            case 18:
                return "������ָ";
            case 19:
                return "���ֻ�ָ";
            case 20:
                return "����Сָ";
            case 97:
                return "���ֲ�ȷ��ָλ";
            case 98:
                return "���ֲ�ȷ��ָλ";
            case 99:
                return "������ȷ��ָλ";
            default:
                return "δ֪";
        }
    }
	
    /*
     * HsOtgApi api = new HsOtgApi(h, MainActivity.this);��ʼ��
     * api.init()����
     * api.Authenticate(200, 200) ����֤  1Ϊ�ɹ�Ȼ��ſ��Զ���
     * api.ReadCard(ici, 200, 1300) iciΪ���֤��   "������" + ic.getPeopleName() + "\n" + "�Ա�" + ic.getSex() + "\n" + "���壺" + ic.getPeople()
                + "\n" + "�������ڣ�" + df.format(ic.getBirthDay()) + "\n" + "��ַ��" + ic.getAddr() + "\n" + "��ݺ��룺"
                + ic.getIDCard() + "\n" + "ǩ�����أ�" + ic.getDepartment() + "\n" + "��Ч���ޣ�" + ic.getStrartDate()
                + "-" + ic.getEndDate() 
                200 Ϊ��������ʱ��  1300Ϊ��������ʱ��
                ����1Ϊ��ȷ
        api.Unpack(filepath, ic.getwltdata())�ڶ����ɹ������  filepath Ϊ�����ľ���·��
        filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wltlib";// ��ȨĿ¼
          ic.getwltdata()Ϊ���֤����Ƭ����
        ����1Ϊ�������ݳɹ���Ƭ���� filepath + "/zp.bmp"
     */
    
}
