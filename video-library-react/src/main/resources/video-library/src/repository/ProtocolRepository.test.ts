import { Protocol } from '../model/Protocol';
import { filmProtocolRepository, filmReviewProtocolRepository } from './ProtocolRepository';

describe('ProtocolRepository', () => {
  Protocol.values.forEach(protocol => {
    test(`should return proper FilmRepository for protocol ${protocol}`, () => {
      expect(filmProtocolRepository[protocol].protocol()).toBe(protocol);
    });

    test(`should return proper FilmReviewRepository for protocol ${protocol}`, () => {
      expect(filmReviewProtocolRepository[protocol].protocol()).toBe(protocol);
    });
  });
});