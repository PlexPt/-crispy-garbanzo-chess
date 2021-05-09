package cn.Jiangyiping.Keynodes;

import java.util.Arrays;
public class Dijskra {
    /*Graph G;
    float [][] Matrix= new float[11][11];
    float [][] distant= new float[11][11];
    // float [][] Weight= new float[11][11]; //距离
    final float MAX = Float.MAX_VALUE;
    public int [][]num = new int[11][11];
    private int Vertexnum;
    private int Edgenum;
    private float weight;
    int u;
    int []Path=new int[11];
    int []Times=new int[11];
    boolean []vis = new boolean [11];
    float []dis = new float[11];
    float[] betweeness = new float[11];
    float []distantsum = new float[11];
    public void Initial(){  //初始化
        for(int i =0;i<11;i++)
        {
            for(int j=0;j<11;j++)
            {
                //    Weight[i][j]=MAX;
                Matrix[i][j]=MAX;
                distant[i][j]=MAX;
            }
            //    Weight[i][i]=0;
            Matrix[i][i]=0;
            vis[i]=false;
            distantsum[i]=0;
        }
       *//* Weight[1][2] = (float) Math.sqrt(1+2*2);
        Weight[1][3]=(float) Math.sqrt(3*3+2*2);
        Weight[1][10]=(float) 2;
        Weight[2][4]=(float) Math.sqrt(1+2*2);
        Weight[3][10]=(float) Math.sqrt(1+2*2);
        Weight[3][6]=(float) Math.sqrt(1+4*4);
        Weight[3][8]=(float) Math.sqrt(1+2*2);
        Weight[4][5]=(float) Math.sqrt(1);
        Weight[5][6]=(float) Math.sqrt(1+2*2);
        Weight[5][10]=(float) Math.sqrt(1+4*4);
        Weight[6][2]=(float)3;
        Weight[6][7]=(float) Math.sqrt(1+1);
        Weight[7][8]=(float) 4;
        Weight[7][9]=(float) Math.sqrt(2*2+2*2);
        Weight[8][9]=(float) Math.sqrt(1+2*2);
        Weight[10][2]=(float) Math.sqrt(1+2*2);
        Weight[10][9]=(float) Math.sqrt(1+5*5);//17E*//*

    }

    public void GetMatrixValue(int Vertexnum,int Edgenum,float Weight[][]){
        this.Vertexnum = Vertexnum;
        this.Edgenum = Edgenum;
        for(int i=1;i<Edgenum;i++)
        {
            for(int j=1;j<Edgenum;j++)
            {
                if(Weight[i][j]<MAX)
                {
                    Matrix[i][j] = Weight[i][j];
                    Matrix[j][i] = Matrix[i][j];
                }
            }
        }
    }
    public float[] DijkstraAlogrim(){
        for(int j=1;j<Vertexnum;j++){  //不同的点
            Arrays.fill(Path, j);
            Arrays.fill(Times,0);
            //j是定点 i表示其他点到定点j的最短路径
            for(int i=1;i<=Vertexnum;i++){
                dis[i] = Matrix[j][i];  //j到其他点的
            }
            vis[j]=true;
            for(int x=1;x<Vertexnum;x++){
                float min=MAX;int temp=0;
                for(int i=1;i<Vertexnum;i++)
                {
                    if(!vis[i]&&dis[i]<min){
                        min=dis[i];  //找到最小值
                        temp=i;
                    }
                }
                vis[temp]=true;  //确定了一个点
                for(int i=1;i<Vertexnum;i++){
                    if(Matrix[temp][i]+dis[temp]<dis[i]){
                        dis[i]=Matrix[temp][i]+dis[temp];//1到V个点  i到点j的最短距离
                        Path[i]=temp;
                    }
                }

            }
            for(int i=1;i<Vertexnum;i++)
            {
                distant[j][i]=dis[i];//j到其他各点的距离
                distantsum[j]=distantsum[j]+distant[j][i];//j点所有最短路径的和
            }
            for(int y=1;y<11;y++){
                if(Path[y]!=j){
                    Times[Path[y]]++;
                    u=Path[y];
                    while(Path[u]!=j)
                    {
                        Times[Path[u]]++;
                        u=Path[u];//算前前节点。
                    }
                }

            }
            //不同j的分界线
            for(int a=1;a<11;a++)
            {
                num[j][a]=Times[a];//记录不同点j到中路径经过点a的次数
            }

        }
        for(int i=1;i<11;i++){
            int s=0;
            for(int j=1;j<11;j++)
            {
                s=s+num[i][j];
                betweeness[i]=s/9; //各个点的介数
            }
            distantsum[i]=distantsum[i]/9;//每个到点i的最短路径之和点数  接近中心性
        }
        //度数怎么计算以及路径判断经过的介数
        return betweeness;
    }*/
}
