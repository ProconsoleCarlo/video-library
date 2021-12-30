import assert from 'assert';
import fetchMockJest from 'fetch-mock-jest';
import { existentFilmReview, insertFilmReview } from '../mock/FilmReviewMock';
import { Protocol } from '../model/Protocol';
import { filmReviewRepository } from './FilmReviewRepository';

describe('FilmReviewRepository', () => {
  const FILM_ID = 1;
  const protocols = Object.keys(Protocol) as Protocol[];
  protocols.forEach((protocol) => {
    describe(`when protocol is ${protocol}`, () => {
      const repository = filmReviewRepository(protocol);

      test('update', async () => {
        fetchMockJest.postOnce(
          (url: string, opts: Request | RequestInit) => {
            assert.deepStrictEqual(JSON.parse(opts.body!.toString()), existentFilmReview);
            return url === `/${protocol}/review/${FILM_ID}`;
          },
          existentFilmReview
        );

        await repository.update(existentFilmReview, FILM_ID)
          .then(it => expect(it).toStrictEqual(existentFilmReview));
      });

      test('insert', async () => {
        fetchMockJest.putOnce(
          (url: string, opts: Request | RequestInit) => {
            assert.deepStrictEqual(JSON.parse(opts.body!.toString()), insertFilmReview);
            return url === `/${protocol}/review/${FILM_ID}`;
          },
          existentFilmReview
        );

        await repository.insert(insertFilmReview, FILM_ID)
          .then(it => expect(it).toStrictEqual(existentFilmReview));
      });
    });
  });
});