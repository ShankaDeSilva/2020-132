from flask import Flask, request
from flask_restful import Resource, Api
from sqlalchemy import create_engine
from json import dumps
from flask_jsonpify import jsonify

db_connect = create_engine('postgresql://postgres:root@localhost:5432/nyc_test')
app = Flask(__name__)
api = Api(app)

class Route(Resource):
    def post(self):
        conn = db_connect.connect() #connect to the database
        print(request.json)
        x1 = request.json['x1']
        y1 = request.json['y1']
        x2 = request.json['x2']
        y2 = request.json['y2']
        query = conn.execute("""select row_to_json(t) from (SELECT x1, y1
FROM pgr_aStar(
   'SELECT id, source, target, st_length(road, true) as cost, distance as reverse_cost, x1, y1, x2, y2 FROM graph',
   (SELECT source FROM graph
    ORDER BY ST_Distance(
        ST_StartPoint(road),
        ST_SetSRID(ST_MakePoint('{0}', '{1}'), 4326),
        true
   ) ASC
   LIMIT 1),
   
  (SELECT source FROM graph
    ORDER BY ST_Distance(
        ST_StartPoint(road),
        ST_SetSRID(ST_MakePoint('{2}', '{3}'), 4326),
        true
   ) ASC
   LIMIT 1)
) as pt
JOIN graph rd ON pt.edge = rd.id) t;""".format(x1, y1, x2, y2))


        
        return {'LatLng': [r[0] for r in query.cursor.fetchall()]}

api.add_resource(Route, '/routes')

if __name__ == "__main__":
    app.run(port='5002',debug=True)