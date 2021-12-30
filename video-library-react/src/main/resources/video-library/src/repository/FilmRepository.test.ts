import assert from 'assert';
import fetchMockJest from 'fetch-mock-jest';
import { existentFilms, insertFilms } from '../mock/FilmsMock';
import { Protocol } from '../model/Protocol';
import { filmRepository } from './FilmRepository';

describe('FilmRepository', () => {
	const protocols = Object.keys(Protocol) as Protocol[];
	protocols.forEach(protocol => {
		describe(`when protocol is ${protocol}`, () => {
			const repository = filmRepository(protocol);

			test('get', async () => {
				fetchMockJest.getOnce(
					(url: string) => url === `/${protocol}/films`,
					existentFilms
				);

				await repository.get()
					.then(it => expect(it).toStrictEqual(existentFilms));
			});

			test('update', async () => {
				fetchMockJest.postOnce(
					(url: string, opts: Request | RequestInit) => {
						assert.deepStrictEqual(JSON.parse(opts.body!.toString()), existentFilms);
						return url === `/${protocol}/films`;
					},
					existentFilms
				);

				await repository.update(existentFilms)
					.then(it => expect(it).toStrictEqual(existentFilms));
			});

			test('insert', async () => {
				fetchMockJest.putOnce(
					(url: string, opts: Request | RequestInit) => {
						assert.deepStrictEqual(JSON.parse(opts.body!.toString()), insertFilms);
						return url === `/${protocol}/films`;
					},
					existentFilms
				);

				await repository.insert(insertFilms)
					.then(it => expect(it).toStrictEqual(existentFilms));
			});
		});
	});
});