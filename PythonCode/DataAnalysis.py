from pymongo import MongoClient
import certifi

ca = certifi.where()
client = MongoClient('mongodb+srv://dog:101401@smartdoghouse.uqibovh.mongodb.net/test',tlsCAFile=ca)

db = client.test 

sum = 0

#개, 고양이 리스트를 추출하고 품종별 연사료비용 분석

doc = {'DBAnimal': True}
catdoc = {'DBAnimal': False}
idfalse = {'DBAnimal':False,'_id':False,'__v':False}




#동물 분류화
doglist = list(db.users.find((doc),idfalse))
catlist = list(db.users.find((catdoc),idfalse)) 

DogSpeciesList = [0 for i in range(45)]
CatSpeciesList = [0 for i in range(45)]

#딕셔너리여서 키값으로 접근이 가능하다.
for i in doglist:
    DogSpeciesList[i['DBSpecies']] += i['DBAmount']

for i in catlist:
    CatSpeciesList[i['DBSpecies']] += i['DBAmount']

#1년 필요 사료량(kg)
for i in range(0,45):
    DogSpeciesList[i] = int(((DogSpeciesList[i]/1000 * 365) / 1000))  #데이터를 1000개 넣고, g 단위이기에 1000을 두 번 나눠줬다.

for i in range(0,25):
    CatSpeciesList[i] = int(((CatSpeciesList[i]/1000 * 365)/1000))

#반려견 고양이의 연 사료 섭취량 출력 (kg)
print("Dog\n\n")
for index, value in enumerate(DogSpeciesList, start=0):
    print(index,value,"kg")

print("\nCat\n\n")
for index,value in enumerate(CatSpeciesList,start=0):
    print("Cat",index,value,"kg")



#쿠팡 가장 많이 팔린 사료 32,000원, 2kg




#연봉별 반려동물 도표
#작은 개는 암수 크기 차이가 없는데 대형견은 수컷의 크기 차이가 있다.
#저급 사료, 중간 사료, 고급 사료 기준으로 가격 세 종류로 나누기 

'''
dogdic = {1 : 0 , 2 : 0, 3 : 0, 4 : 0, 5 : 0, 6 : 0, 7: 0, 8:0 , 9 : 0, 10 : 0, 11:0,12:0,13:0,14:0,15:0,16:0,17:0,18:0,19:0,20:0
,21:0,22:0,23:0,24:0,25:0,26:0,27:0,28:0,29:0,30:0,31:0,32:0,33:0}

catdic = {1:0,2:0,3:0,4:0,5:0,6:0,7:0,8:0,9:0,10:0,11:0,12:0,13:0,14:0,15:0}

for i in doglist:
   dogdic[i['DBSpecies']] += i['DBAmount']

for i in catlist:
    catdic[i['DBSpecies']] += i['DBAmount']

for i in range(1,len(dogdic)):
    dogdic[i] = int((dogdic[i]/1000 * 365) /1000)   #데이터를 1000개 넣고, g 단위이기에 1000을 두 번 나눠줬다.
    print(i,dogdic[i])

for i in range(1,len(catdic)):
    catdic[i] = int((catdic[i]/1000 * 365) / 1000)
    print(i,catdic[i])


'''