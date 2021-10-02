#include <stdlib.h>
#include <graphics.h>
#include <windows.h>
#include <conio.h>
#include <graphics.h>
#include <windows.h>
#include <stdio.h>
#include <mmsystem.h>
#define _CRT_SECURE_NO_WARNINGS
#pragma warning (disable: 4996)
#pragma comment(lib,"winmm.lib")

void initgamePicture();   //������ϷͼƬ
void SetPlayer();       //��ʾ��ɫ��Ϣ
void initPlayer();      //��ʼ����Ϸ��ɫ
void SetMap();         //������Ϸ��ͼ
char *intToString(int Number); //������ת�����ַ���
void playGame();          //��ʼ��Ϸ
void NewGame();        //����Ϸ
void SetItem();
void Change(int h, int temp);//����¥�������ҵĳ�ʼλ��
void CaLv();                //����ȼ�
void Messages();           //��Ϸ˵��
void Music();
int Combat(int x);
int VS(int playHP, int playATT, int playDEF, int monHP, int monATT, int monDEF);

int playerx, playery, playerh;
char str[20] = "";
//��ͼ1
int map[10][13][13];
IMAGE Wall, Ground, Green_Slime, Red_Slime, Blue_Cry, Red_Cry, Blue_Key, Yellow_Key,
Red_Med, Blue_Med, Ladder, Small_Skull, Big_Skull, Small_Bat, Small_Wizard,
Blue_door, Yellow_door, Player, Message, Item, a;
HWND hwnd;
struct gamerole
{
	char name[20] = "�ڵ�";  //�������
	int HP;	    //Ѫ��
	int MP;
	int DEF;	//����
	int ATT;    //����
	int Lv;     //�ȼ�
	int Exp;    //����
	int Num_Blue_Key; //��Կ������
	int Num_Yellow_Key;
}player;

struct monster    //��������
{
	int HP;	    //Ѫ��
	int ATT;    //����
	int DEF;	//����
	int Exp;    //����
};
struct monster Green_Slime_Pro = { 50,10,12,100 };    //��ʷ��ķ����     
struct monster Red_Slime_Pro = { 60, 60, 12, 500 };  //��ʷ��ķ����
struct monster Small_Wizard_Pro = { 100, 30, 9, 400 };//С��ʦ����
struct monster Small_Bat_Pro = { 20, 10, 9, 50 };         //С��������
struct monster Small_Skull_Pro = { 30, 20, 10, 200 };   //С��������
struct monster Big_Skull_Pro = { 60, 50, 25, 300 };     //����������


int main()
{
	Music();
	hwnd = initgraph(60 * 14, 60 * 13);
	initgamePicture();

	SetItem();
	while (1) {
		SetMap();
		SetPlayer();
		playGame();
	}

	return 0;
}

/*
*�µ���Ϸ
*/
void NewGame()
{
	initPlayer(); //��ʼ����Ϸ��ɫ��Ϣ
	FILE *fp; //��ȡ������map.txt�еĵ�ͼ
	fp = fopen("map.txt", "r");
	for(int k=0; k<10; k++)
		for (int i = 0; i < 13; i++)
			for(int j = 0; j < 13; j++)
				fscanf(fp, "%d", &map[k][i][j]);
	fclose(fp);
}
/*
*������Ϸ
*/
void KeepGame()
{
	FILE *fp; //��ȡ������keepmap.txt�еĵ�ͼ
	fp = fopen("keepmap.txt", "r");
	for (int k = 0; k < 10; k++)
		for (int i = 0; i < 13; i++)
			for (int j = 0; j < 13; j++)
				fscanf(fp, "%d", &map[k][i][j]);

	fscanf(fp, "%d", &playerh);
	fscanf(fp, "%d", &playerx);
	fscanf(fp, "%d", &playery);

	fscanf(fp, "%s", &player.name);
	fscanf(fp, "%d", &player.HP);
	fscanf(fp, "%d", &player.MP);
	fscanf(fp, "%d", &player.DEF);
	fscanf(fp, "%d", &player.ATT);
	fscanf(fp, "%d", &player.Lv);
	fscanf(fp, "%d", &player.Exp);
	fscanf(fp, "%d", &player.Num_Blue_Key);
	fscanf(fp, "%d", &player.Num_Yellow_Key);
	fclose(fp);
}
/*
*������Ϸ
*/
void SaveGame()
{
	FILE *fp;
	fp = fopen("keepmap.txt", "w");
	for (int k = 0; k < 10; k++) {
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++)
				fprintf(fp, "%d ", map[k][i][j]);
			fprintf(fp, "\n");
		}
		fprintf(fp, "\n");
	}
	fprintf(fp, "%d\n", playerh);
	fprintf(fp, "%d\n", playerx);
	fprintf(fp, "%d\n", playery);

	fprintf(fp, "%s\n", player.name);
	fprintf(fp, "%d\n", player.HP);
	fprintf(fp, "%d\n", player.MP);
	fprintf(fp, "%d\n", player.DEF);
	fprintf(fp, "%d\n", player.ATT);
	fprintf(fp, "%d\n", player.Lv);
	fprintf(fp, "%d\n", player.Exp);
	fprintf(fp, "%d\n", player.Num_Blue_Key);
	fprintf(fp, "%d\n", player.Num_Yellow_Key);

	fclose(fp);
}

/*��Ϸ��ʼ���ĸ�ѡ�
*n�µ���Ϸ
*j������Ϸ
*c��Ϸ˵��
*e�˳���Ϸ
*/
void SetItem()
{
	putimage(0, 0, &Item);
	while (1) {
		char ch = _getch();
		switch (ch) {
			case 'N':                //�µ���Ϸ
			case 'n':NewGame(); return;
				break;

			case 'J':					//������Ϸ
			case 'j':KeepGame(); return;
				break;

			case 'C':               //��Ϸ˵��
			case 'c': Messages();  
				break;

			case 'E':     //�˳���Ϸ
			case 'e':exit(0); return;
				break;

		}
	}


}

//���ݲ�ͬ¥����ʾ��ɫ�ĳ�ʼλ��
void Change(int h, int temp)
{
	switch (h)
	{
		case 0:
			if (temp == 0) {
				playerx = 1;
				playery = 2;
				map[playerh][playerx][playery] = 99;
			}
			break;
		case 1:
			if (temp == 1) {
				playerx = 11;
				playery = 10;
				map[playerh][playerx][playery] = 99;
			}
			else {
				playerx = 2;
				playery = 1;
				map[playerh][playerx][playery] = 99;
			}
			break; 
		case 2:
			if (temp == 1) {
				playerx = 7;
				playery = 2;
				map[playerh][playerx][playery] = 99;
			}
			else
			{
				playerx = 8;
				playery = 10;
				map[playerh][playerx][playery] = 99;
			}
			break;
		case 3:
			if (temp == 1) {
				playerx = 11;
				playery = 2;
				map[playerh][playerx][playery] = 99;
			}
			else
			{
				playerx = 4;
				playery = 11;
				map[playerh][playerx][playery] = 99;
			}
			break;
		case 4:
			if (temp == 1) {
				playerx = 11;
				playery = 5;
				
				map[playerh][playerx][playery] = 99;
			}
			else
			{
				playerx = 1;
				playery = 2;
				map[playerh][playerx][playery] = 99;
			}
			break;
		case 5:
			if (temp == 1) {
				playerx = 6;
				playery = 7;
				map[playerh][playerx][playery] = 99;
			}
			else
			{
				playerx = 4;
				playery = 7;
				map[playerh][playerx][playery] = 99;
			}
			break;
		case 6:
			if (temp == 1) {
				playerx = 10;
				playery = 6;
				map[playerh][playerx][playery] = 99;
			}
			else
			{
				playerx = 2;
				playery = 6;
				map[playerh][playerx][playery] = 99;
			}
			break;
		case 7:
			if (temp == 1) {
				playerx = 3;
				playery = 8;
				map[playerh][playerx][playery] = 99;
			}
			else
			{
				playerx = 3;
				playery = 4;
				map[playerh][playerx][playery] = 99;
			}
			break;
		case 8:
			if (temp == 1) {
				playerx = 2;
				playery = 11;
				map[playerh][playerx][playery] = 99;
			}
			else
			{
				playerx = 10;
				playery = 11;
				map[playerh][playerx][playery] = 99;
			}
			break;
		case 9:
			if (temp == 1) {
				playerx = 10;
				playery = 6;
				map[playerh][playerx][playery] = 99;
			}
			
			break;
	}
}
/*
*��ʾ��ɫ��Ϣ
*/
void SetPlayer()
{
	putimage(60 * 13, 0, &Message);
	outtextxy(60 * 13 + 12, 100, player.name);
	outtextxy(60 * 13 + 12, 180, intToString(player.Lv));
	outtextxy(60 * 13 + 12, 235, intToString(player.Exp));
	outtextxy(60 * 13 + 12, 362, intToString(player.HP));
	outtextxy(60 * 13 + 12, 425, intToString(player.MP));
	outtextxy(60 * 13 + 12, 517, intToString(player.ATT));
	outtextxy(60 * 13 + 12, 567, intToString(player.DEF));
	outtextxy(60 * 13 + 12, 689, intToString(player.Num_Yellow_Key));
	outtextxy(60 * 13 + 12, 759, intToString(player.Num_Blue_Key));
}


void Music()
{
	PlaySound(TEXT("1.wav"), 0, SND_FILENAME|SND_ASYNC|SND_LOOP);
}
/*
*   ������ϷͼƬ
*/
void initgamePicture()
{
	loadimage(&Wall, "ǽ.jpg", 60, 60);
	loadimage(&Ground, "�ذ�.jpg", 60, 60);
	loadimage(&Green_Slime, "��ʷ��ķ.jpg", 60, 60);
	loadimage(&Red_Slime, "��ʷ��ķ.jpg", 60, 60);

	loadimage(&Blue_Cry, "��ˮ��.jpg", 60, 60);
	loadimage(&Red_Cry, "��ˮ��.jpg", 60, 60);

	loadimage(&Blue_Key, "��Կ��.jpg", 60, 60);
	loadimage(&Yellow_Key, "��Կ��.jpg", 60, 60);

	loadimage(&Red_Med, "С��ҩˮ.jpg", 60, 60);
	loadimage(&Blue_Med, "С��ҩˮ.jpg", 60, 60);

	loadimage(&Ladder, "����.jpg", 60, 60);
	loadimage(&Small_Bat, "С����.jpg", 60, 60);
	loadimage(&Small_Wizard, "С��ʦ.jpg", 60, 60);
	loadimage(&Small_Skull, "���ñ�.jpg", 60, 60);
	loadimage(&Big_Skull, "�����ñ�.jpg", 60, 60);

	loadimage(&Blue_door, "����.jpg", 60, 60);
	loadimage(&Yellow_door, "����.jpg", 60, 60);
	loadimage(&Player, "��.jpg", 60, 60);
	loadimage(&Message, "info.jpg");
	loadimage(&a, "��Ϸ˵��.jpg", 840, 780);
	//loadimage(&a, "ħ��.jpeg", 840, 780);
	loadimage(&Item, "�˵�.jpg", 840, 780);
}

/*
*��ʼ����Ϸ��ɫ
*/
void initPlayer()
{
	player.Lv = 0;
	player.ATT = 50;
	player.DEF = 50;
	player.Num_Blue_Key = 0;
	player.Num_Yellow_Key = 0;
	player.HP = 500;
	player.MP = 250;
	player.Exp = 0;
	playerx = 11;
	playery = 6;
	playerh = 0;
}

//����ת��Ϊ�ַ�
char *intToString(int Number)
{
	int len = 0;

	if (Number == 0) {
		str[0] = '0';
		len++;
	}
	while (Number)
	{
		str[len++] = Number % 10 + '0';
		Number /= 10;
	}
	for (int i = 0; i < len / 2; i++) {
		char t = str[i];
		str[i] = str[len - i - 1];
		str[len - i - 1] = t;
	}
	str[len] = '\0';
	return str;
}

/*
*������Ϸ��ͼ
*
*/
void SetMap()
{
	for (int i = 0; i < 13; i++)
	{
		for (int j = 0; j < 13; j++)
		{
			switch (map[playerh][i][j])
			{

			case 0:
				putimage(j * 60, i * 60, &Wall);          //ǽ
				break;
			case 1:
				putimage(j * 60, i * 60, &Ground);        //�ذ�
				break;
			case 2:
				putimage(j * 60, i * 60, &Blue_door);     //����
				break;
			case 3:
				putimage(j * 60, i * 60, &Yellow_door);    //����
				break;
			case 4:
				putimage(j * 60, i * 60, &Blue_Cry);         //��ˮ��
				break;
			case 5:
				putimage(j * 60, i * 60, &Red_Cry);          //��ˮ��
				break;
			case 6:
				putimage(j * 60, i * 60, &Blue_Key);        //��Կ��
				break;
			case 7:
				putimage(j * 60, i * 60, &Yellow_Key);     //��Կ��
				break;
			case 8:
				putimage(j * 60, i * 60, &Red_Med);          //��ҩˮ
				break;
			case 9:
				putimage(j * 60, i * 60, &Blue_Med);         //��ҩˮ
				break;
			case 10:
				putimage(j * 60, i * 60, &Small_Bat);        //С����
				break;
			case 11:
				putimage(j * 60, i * 60, &Small_Wizard);     //С��ʦ
				break;
			case 12:
				putimage(j * 60, i * 60, &Small_Skull);      //С����
				break;
			case 13:
				putimage(j * 60, i * 60, &Big_Skull);        //������
				break;
			case 14:
				putimage(j * 60, i * 60, &Green_Slime);      //��ʷ��ķ
				break;
			case 15:
				putimage(j * 60, i * 60, &Red_Slime);        //��ʷ��ķ
				break;
			case 97:
			case 98:
				putimage(j * 60, i * 60, &Ladder);         //��¥���Ӻ���¥����
				break;
			case 99:
				putimage(j * 60, i * 60, &Player);          //���
				break;
			}

		}
	}
}

int Combat(int x)
{
	int ID;
	switch (x) {
	case 10:
		ID = MessageBox(hwnd, "С����", "�Ƿ񹥻���", MB_YESNO);
		if (ID == IDYES)
		{
			if (VS(player.HP, player.ATT, player.DEF, Small_Bat_Pro.HP, Small_Bat_Pro.ATT, Small_Bat_Pro.DEF)) {
				player.Exp += Small_Bat_Pro.Exp;
				CaLv();
				return 1;
			}
		}
		break;
	case 11:
		ID = MessageBox(hwnd, "����С��ʦ", "�Ƿ񹥻���", MB_YESNO);
		if (ID == IDYES)
		{
			if (VS(player.HP, player.ATT, player.DEF, Small_Wizard_Pro.HP, Small_Wizard_Pro.ATT, Small_Wizard_Pro.DEF)) {
				player.Exp += Small_Wizard_Pro.Exp;
				CaLv();
				return 1;
			}
		}
		break;
	case 12:
		ID = MessageBox(hwnd, "����С����", "�Ƿ񹥻���", MB_YESNO);
		if (ID == IDYES)
		{
			if (VS(player.HP, player.ATT, player.DEF, Small_Skull_Pro.HP, Small_Skull_Pro.ATT, Small_Skull_Pro.DEF)) {
				player.Exp += Small_Skull_Pro.Exp;
				CaLv();
				return 1;
			}
		}
		break;
	case 13:
		ID = MessageBox(hwnd, "����������", "�Ƿ񹥻���", MB_YESNO);
		if (ID == IDYES)
		{
			if (VS(player.HP, player.ATT, player.DEF, Big_Skull_Pro.HP, Big_Skull_Pro.ATT, Big_Skull_Pro.DEF)) {
				player.Exp += Big_Skull_Pro.Exp;
				CaLv();
				return 1;
			}
		}
		break;
	case 14:
		ID = MessageBox(hwnd, "������ʷ��ķ", "�Ƿ񹥻���", MB_YESNO);
		if (ID == IDYES)
		{
			if (VS(player.HP, player.ATT, player.DEF, Green_Slime_Pro.HP, Green_Slime_Pro.ATT, Green_Slime_Pro.DEF)) {
				player.Exp += Green_Slime_Pro.Exp;
				CaLv();
				return 1;
			}
		}
		break;
	case 15:
		ID = MessageBox(hwnd, "������ʷ��ķ", "�Ƿ񹥻���", MB_YESNO);
		if (ID == IDYES)
		{
			if (VS(player.HP, player.ATT, player.DEF, Red_Slime_Pro.HP, Red_Slime_Pro.ATT, Red_Slime_Pro.DEF)) {
				player.Exp += Red_Slime_Pro.Exp;
				CaLv();
				return 1;
			}
		}
		break;

	}
	return 0;
}

//����ȼ�
void CaLv()
{
	if (player.Exp >= player.Lv * 1000) {
		player.Exp -= player.Lv * 1000;
		player.Lv++;
		player.ATT += 10;
		player.DEF += 10;
		player.HP += 100;
	}

}

//��Ϸ˵��
void Messages()
{
	putimage(0, 0, &a);
	_getch();
	SetItem();
}

//����ս���Ƿ�ʤ��
int VS(int playHP, int playATT, int playDEF, int monHP, int monATT, int monDEF)
{
	if (playDEF > monATT)
		return 1;
	while (playHP > 0 || monHP > 0)
	{
		monHP -= (playATT - monDEF);
		if (monHP < 0)
			break;
		playHP -= (monATT - playDEF);
	}
	if (playHP > 0) {
		player.HP = playHP;
		return 1;
	}

	else {
		MessageBox(hwnd, "", "�򲻹�", MB_YESNO);
		return 0;
	}
}


void playGame()
{
	while (1)
	{
		int ID;
		char ch = _getch();
		switch (ch) {
		case 'q':
		case 'Q':
			ID = MessageBox(hwnd, "������Ϸ", "�Ƿ񱣴���Ϸ��", MB_YESNO);
			if (ID == IDYES)
				SaveGame();
			break;
		case 'w':
		case 'W':
		case 72:
			if (map[playerh][playerx - 1][playery] == 1) {         //��һ���ǵذ�
				map[playerh][playerx - 1][playery] = 99;
				map[playerh][playerx][playery] = 1;
				playerx--;
			}
			else if (map[playerh][playerx - 1][playery] == 6) {      //��һ������Կ��
				player.Num_Blue_Key++;
				map[playerh][playerx - 1][playery] = 99;
				map[playerh][playerx][playery] = 1;
				playerx--;
			}
			else if (map[playerh][playerx - 1][playery] == 7) {     //��һ���ǻ�Կ��
				player.Num_Yellow_Key++;
				map[playerh][playerx - 1][playery] = 99;
				map[playerh][playerx][playery] = 1;
				playerx--;
			}
			//��һ���ǹ���
			else if (map[playerh][playerx - 1][playery] == 10 || map[playerh][playerx - 1][playery] == 11 ||
				map[playerh][playerx - 1][playery] == 12 || map[playerh][playerx - 1][playery] == 13 ||
				map[playerh][playerx - 1][playery] == 14 || map[playerh][playerx - 1][playery] == 15)
			{
				int x = Combat(map[playerh][playerx - 1][playery]);
				if (x == 1) {
					map[playerh][playerx - 1][playery] = 99;
					map[playerh][playerx][playery] = 1;
					playerx--;
				}
			}
			//����ҩˮ
			else if (map[playerh][playerx - 1][playery] == 8 || map[playerh][playerx - 1][playery] == 9) {
				if (map[playerh][playerx - 1][playery] == 8)
					player.HP += 200;   //��ɫҩˮ��200hp
				else
					player.HP += 500;  //��ɫҩˮ��500hp
				map[playerh][playerx - 1][playery] = 99;
				map[playerh][playerx][playery] = 1;
				playerx--;
			}
			//������
			else if (map[playerh][playerx - 1][playery] == 2 || map[playerh][playerx - 1][playery] == 3) {
				if (map[playerh][playerx - 1][playery] == 2 && player.Num_Blue_Key) {
					player.Num_Blue_Key--;
					map[playerh][playerx - 1][playery] = 99;
					map[playerh][playerx][playery] = 1;
					playerx--;
				}
				if (map[playerh][playerx - 1][playery] == 3 && player.Num_Yellow_Key) {
					player.Num_Yellow_Key--;
					map[playerh][playerx - 1][playery] = 99;
					map[playerh][playerx][playery] = 1;
					playerx--;
				}
			}
			//����ˮ��
			//��ˮ��+2����
			//��ˮ��+2����
			else if (map[playerh][playerx - 1][playery] == 4 || map[playerh][playerx - 1][playery] == 5) {
				if (map[playerh][playerx - 1][playery] == 4)
					player.DEF += 2;
				else if (map[playerh][playerx - 1][playery] == 5)
					player.ATT += 2;
				map[playerh][playerx - 1][playery] = 99;
				map[playerh][playerx][playery] = 1;
				playerx--;
			}
			//��һ�������ϵ�¥��
			else if (map[playerh][playerx - 1][playery] == 98) {   
				map[playerh][playerx][playery] = 1;
				playerh++;
				Change(playerh, 1);
			}
			//��һ�������µ�¥��
			else if (map[playerh][playerx - 1][playery] == 97) {
				map[playerh][playerx][playery] = 1;
				playerh--;
				Change(playerh, 0);
			}
			break;
		case 's':
		case 'S':
		case 80:
			if (map[playerh][playerx + 1][playery] == 1) {         //��һ���ǵذ�
				map[playerh][playerx + 1][playery] = 99;
				map[playerh][playerx][playery] = 1;
				playerx++;
			}
			else if (map[playerh][playerx + 1][playery] == 6) {      //��һ������Կ��
				player.Num_Blue_Key++;
				map[playerh][playerx + 1][playery] = 99;
				map[playerh][playerx][playery] = 1;
				playerx++;
			}
			else if (map[playerh][playerx + 1][playery] == 7) {     //��һ���ǻ�Կ��
				player.Num_Yellow_Key++;
				map[playerh][playerx + 1][playery] = 99;
				map[playerh][playerx][playery] = 1;
				playerx++;
			}
			//��һ���ǹ���
			else if (map[playerh][playerx + 1][playery] == 10 || map[playerh][playerx + 1][playery] == 11 ||
				map[playerh][playerx + 1][playery] == 12 || map[playerh][playerx + 1][playery] == 13 ||
				map[playerh][playerx + 1][playery] == 14 || map[playerh][playerx + 1][playery] == 15)
			{
				int x = Combat(map[playerh][playerx + 1][playery]);
				if (x == 1) {
					map[playerh][playerx + 1][playery] = 99;
					map[playerh][playerx][playery] = 1;
					playerx++;
				}
			}
			//����ҩˮ
			else if (map[playerh][playerx + 1][playery] == 8 || map[playerh][playerx + 1][playery] == 9) {
				if (map[playerh][playerx + 1][playery] == 8)
					player.HP += 200;
				else
					player.HP += 500;
				map[playerh][playerx + 1][playery] = 99;
				map[playerh][playerx][playery] = 1;
				playerx++;
			}
			//������
			else if (map[playerh][playerx + 1][playery] == 2 || map[playerh][playerx + 1][playery] == 3) {
				if (map[playerh][playerx + 1][playery] == 2 && player.Num_Blue_Key) {
					player.Num_Blue_Key++;
					map[playerh][playerx + 1][playery] = 99;
					map[playerh][playerx][playery] = 1;
					playerx++;
				}
				if (map[playerh][playerx + 1][playery] == 3 && player.Num_Yellow_Key) {
					player.Num_Yellow_Key++;
					map[playerh][playerx + 1][playery] = 99;
					map[playerh][playerx][playery] = 1;
					playerx++;
				}
			}
			//����ˮ��
			//��ˮ��+2����
			//��ˮ��+2����
			else if (map[playerh][playerx + 1][playery] == 4 || map[playerh][playerx + 1][playery] == 5) {
				if (map[playerh][playerx + 1][playery] == 4)
					player.DEF += 2;
				else if (map[playerh][playerx + 1][playery] == 5)
					player.ATT += 2;
				map[playerh][playerx + 1][playery] = 99;
				map[playerh][playerx][playery] = 1;
				playerx++;
			}
			//��һ��������¥��
			else if (map[playerh][playerx + 1][playery] == 98) {
				map[playerh][playerx][playery] = 1;
				playerh++;
				Change(playerh, 1);
			}
			else if (map[playerh][playerx + 1][playery] == 97) {
				map[playerh][playerx][playery] = 1;
				playerh--;
				Change(playerh, 0);
			}
			break;
		case 'a':
		case 75:
			if (map[playerh][playerx][playery - 1] == 1) {         //��һ���ǵذ�
				map[playerh][playerx][playery - 1] = 99;
				map[playerh][playerx][playery] = 1;
				playery--;
			}
			else if (map[playerh][playerx][playery - 1] == 6) {      //��һ������Կ��
				player.Num_Blue_Key++;
				map[playerh][playerx][playery - 1] = 99;
				map[playerh][playerx][playery] = 1;
				playery--;
			}
			else if (map[playerh][playerx][playery - 1] == 7) {     //��һ���ǻ�Կ��
				player.Num_Yellow_Key++;
				map[playerh][playerx][playery - 1] = 99;
				map[playerh][playerx][playery] = 1;
				playery--;
			}
			//��һ���ǹ���
			else if (map[playerh][playerx][playery - 1] == 10 || map[playerh][playerx][playery - 1] == 11 ||
				map[playerh][playerx][playery - 1] == 12 || map[playerh][playerx][playery - 1] == 13 ||
				map[playerh][playerx][playery - 1] == 14 || map[playerh][playerx][playery - 1] == 15)
			{
				int x = Combat(map[playerh][playerx][playery - 1]);
				if (x == 1) {
					map[playerh][playerx][playery - 1] = 99;
					map[playerh][playerx][playery] = 1;
					playery--;
				}
			}
			//����ҩˮ
			else if (map[playerh][playerx][playery - 1] == 8 || map[playerh][playerx][playery - 1] == 9) {
				if (map[playerh][playerx][playery - 1] == 8)
					player.HP += 200;
				else
					player.HP += 500;
				map[playerh][playerx][playery - 1] = 99;
				map[playerh][playerx][playery] = 1;
				playery--;
			}
			//������
			else if (map[playerh][playerx][playery - 1] == 2 || map[playerh][playerx][playery - 1] == 3) {
				if (map[playerh][playerx][playery - 1] == 2 && player.Num_Blue_Key) {
					player.Num_Blue_Key--;
					map[playerh][playerx][playery - 1] = 99;
					map[playerh][playerx][playery] = 1;
					playery--;
				}
				if (map[playerh][playerx][playery - 1] == 3 && player.Num_Yellow_Key) {
					player.Num_Yellow_Key--;
					map[playerh][playerx][playery - 1] = 99;
					map[playerh][playerx][playery] = 1;
					playery--;
				}
			}
			//����ˮ��
			//��ˮ��+2����
			//��ˮ��+2����
			else if (map[playerh][playerx][playery - 1] == 4 || map[playerh][playerx][playery - 1] == 5) {
				if (map[playerh][playerx][playery - 1] == 4)
					player.DEF += 2;
				else if (map[playerh][playerx][playery - 1] == 5)
					player.ATT += 2;
				map[playerh][playerx][playery - 1] = 99;
				map[playerh][playerx][playery] = 1;
				playery--;
			}
			//��һ��������¥��
			else if (map[playerh][playerx][playery - 1] == 98) {
				map[playerh][playerx][playery] = 1;
				playerh++;
				Change(playerh, 1);
			}
			else if (map[playerh][playerx][playery - 1] == 97) {
				map[playerh][playerx][playery] = 1;
				playerh--;
				Change(playerh, 0);
			}
			break;
		case 'd':
		case 'D':
		case 77:
			if (map[playerh][playerx][playery + 1] == 1) {         //��һ���ǵذ�
				map[playerh][playerx][playery + 1] = 99;
				map[playerh][playerx][playery] = 1;
				playery++;
			}
			else if (map[playerh][playerx][playery + 1] == 6) {      //��һ������Կ��
				player.Num_Blue_Key++;
				map[playerh][playerx][playery + 1] = 99;
				map[playerh][playerx][playery] = 1;
				playery++;
			}
			else if (map[playerh][playerx][playery + 1] == 7) {     //��һ���ǻ�Կ��
				player.Num_Yellow_Key++;
				map[playerh][playerx][playery + 1] = 99;
				map[playerh][playerx][playery] = 1;
				playery++;
			}
			//��һ���ǹ���
			else if (map[playerh][playerx][playery + 1] == 10 || map[playerh][playerx][playery + 1] == 11 ||
				map[playerh][playerx][playery + 1] == 12 || map[playerh][playerx][playery + 1] == 13 ||
				map[playerh][playerx][playery + 1] == 14 || map[playerh][playerx][playery + 1] == 15)
			{
				int x = Combat(map[playerh][playerx][playery + 1]);
				if (x == 1) {
					map[playerh][playerx][playery + 1] = 99;
					map[playerh][playerx][playery] = 1;
					playery++;
				}
			}
			//����ҩˮ
			else if (map[playerh][playerx][playery + 1] == 8 || map[playerh][playerx][playery + 1] == 9) {
				if (map[playerh][playerx][playery + 1] == 8)
					player.HP += 200;
				else
					player.HP += 500;
				map[playerh][playerx][playery + 1] = 99;
				map[playerh][playerx][playery] = 1;
				playery++;
			}
			//������
			else if (map[playerh][playerx][playery + 1] == 2 || map[playerh][playerx][playery + 1] == 3) {
				if (map[playerh][playerx][playery + 1] == 2 && player.Num_Blue_Key) {
					player.Num_Blue_Key--;
					map[playerh][playerx][playery + 1] = 99;
					map[playerh][playerx][playery] = 1;
					playery++;
				}
				if (map[playerh][playerx][playery + 1] == 3 && player.Num_Yellow_Key) {
					player.Num_Yellow_Key--;
					map[playerh][playerx][playery + 1] = 99;
					map[playerh][playerx][playery] = 1;
					playery++;
				}
			}
			//����ˮ��
			//��ˮ��+2����
			//��ˮ��+2����
			else if (map[playerh][playerx][playery + 1] == 4 || map[playerh][playerx][playery + 1] == 5) {
				if (map[playerh][playerx][playery + 1] == 4)
					player.DEF += 2;
				else if (map[playerh][playerx][playery + 1] == 5)
					player.ATT += 2;
				map[playerh][playerx][playery + 1] = 99;
				map[playerh][playerx][playery] = 1;
				playery++;
			}

			//��һ��������¥��
			else if (map[playerh][playerx][playery + 1] == 98) {
				map[playerh][playerx][playery] = 1;
				playerh++;
				Change(playerh, 1);
			}
			else if (map[playerh][playerx][playery + 1] == 97) {
				map[playerh][playerx][playery] = 1;
				playerh--;
				Change(playerh, 0);
			}
			break;
		}
		SetMap();  //������ʾ��ͼ
		SetPlayer(); //������ʾ��ɫ��Ϣ
	}
}

