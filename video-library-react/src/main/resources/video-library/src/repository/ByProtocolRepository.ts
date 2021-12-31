import { Protocol } from '../model/Protocol';

export interface ByProtocolRepository {
  protocol(): Protocol;
}