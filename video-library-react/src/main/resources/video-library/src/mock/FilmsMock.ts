import { Film } from '../model/Film';

export const existentFilms: Film[] = [
  {
    'id': 1,
    'title': 'Updated title',
    'year': 2012,
    'genres': [
      {
        'id': 1,
        'value': 'ACTION'
      },
      {
        'id': 4,
        'value': 'ROMANTIC'
      }
    ],
    'reviews': [
      {
        'id': 1,
        'date': '2013-12-31T00:00:00',
        'rating': 8,
        'detail': 'Updated review'
      }
    ]
  },
  {
    'id': 2,
    'title': '007 - 01 Licenza di uccidere',
    'year': 1962,
    'genres': [
      {
        'id': 3,
        'value': 'COMEDY'
      },
      {
        'id': 2,
        'value': 'ADVENTURE'
      },
      {
        'id': 5,
        'value': 'THRILLER'
      }
    ],
    'reviews': [
      {
        'id': 2,
        'date': '2013-10-18T00:00:00',
        'rating': 10,
        'detail': 'Molto bello'
      }
    ]
  }
];

export const insertFilms: Film[] = [
  {
    'id': null,
    'title': 'Updated title',
    'year': 2012,
    'genres': [
      {
        'id': 1,
        'value': 'ACTION'
      },
      {
        'id': 4,
        'value': 'ROMANTIC'
      }
    ],
    'reviews': [
      {
        'id': null,
        'date': '2013-12-31T00:00:00',
        'rating': 8,
        'detail': 'Updated review'
      }
    ]
  },
  {
    'id': null,
    'title': '007 - 01 Licenza di uccidere',
    'year': 1962,
    'genres': [
      {
        'id': 3,
        'value': 'COMEDY'
      },
      {
        'id': 2,
        'value': 'ADVENTURE'
      },
      {
        'id': 5,
        'value': 'THRILLER'
      }
    ],
    'reviews': [
      {
        'id': null,
        'date': '2013-10-18T00:00:00',
        'rating': 10,
        'detail': 'Molto bello'
      }
    ]
  }
];